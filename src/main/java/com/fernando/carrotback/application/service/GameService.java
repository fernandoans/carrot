package com.fernando.carrotback.application.service;

import com.fernando.carrotback.presentation.dto.ResponseQuestionDTO;
import com.fernando.carrotback.presentation.dto.ResponseRankingDTO;
import com.fernando.carrotback.domain.model.Game;
import com.fernando.carrotback.domain.model.Player;
import com.fernando.carrotback.domain.model.PlayerAnswer;
import com.fernando.carrotback.domain.model.Question;
import com.fernando.carrotback.domain.repository.GameRepository;
import com.fernando.carrotback.domain.repository.PlayerAnswerRepository;
import com.fernando.carrotback.domain.repository.PlayerRepository;
import com.fernando.carrotback.domain.repository.QuestionRepository;
import com.fernando.carrotback.infrastructure.enums.GameStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository repository;
    private final QuestionRepository questionRepository;
    private final PlayerRepository playerRepository;
    private final PlayerAnswerRepository playerAnswerRepository;
    // Ciclo de Vida
    private final GameTimeService timeService;

    @Transactional
    public int processFileCsv(MultipartFile file) {
        int totQuestoes = 0;
        String nome = this.validarArquivo(file);
        // Limpar toda a base
        clearDatabase();
        // Carregar o arquivo CSV
        try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String linha = reader.readLine(); // Retira a linha do cabeçalho
            while ((linha = reader.readLine()) != null) {
                saveQuestion(linha.split(";"));
                totQuestoes++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar o arquivo!", e);
        }
        // Carregar o Game
        if (totQuestoes == 0) {
            throw new RuntimeException("Nenhuma questão processada, verifique o arquivo!");
        }
        this.startGame(nome, totQuestoes);
        cycleWaiting();
        // Send a trigger message to the index page via WebSocket topic
        return totQuestoes;
    }

    public ResponseQuestionDTO getQuestion(boolean atualiza) {
        Game game = repository.findAll().stream().findFirst()
          .orElseThrow(() -> new NoSuchElementException("Jogo não encontrado, verifique os dados!"));
        Question question = questionRepository.findById(game.getActualQuestion())
          .orElseThrow(() -> new NoSuchElementException("Questão não encontrada, verifique os dados!"));
        if (atualiza) {
            game.setActualQuestion(game.getActualQuestion() + 1);
            repository.save(game);
        }
        return ResponseQuestionDTO.toResponse(question);
    }

    public List<ResponseRankingDTO> getRanking() {
        Game entity = repository.findAll().stream().findFirst()
          .orElseThrow(() -> new NoSuchElementException("Jogo não encontrado, verifique os dados!"));
        repository.save(entity);
        // Atualizar scores dos jogadores
        actualScores();
        return playerRepository.findTop20ByOrderByScoreDesc()
          .stream()
          .map(ResponseRankingDTO::toResponse)
          .toList();
    }

    private void clearDatabase() {
        repository.deleteAllInBatch();
        playerAnswerRepository.deleteAllInBatch();
        questionRepository.deleteAllInBatch();
        playerAnswerRepository.deleteAllInBatch();
    }

    private void saveQuestion(String [] colunas) {
        if (colunas.length == 8) {
            Question question = new Question();
            question.setOrder(Long.parseLong(colunas[0]));
            question.setDescription(colunas[1]);
            question.setAnswer1(colunas[2]);
            question.setAnswer2(colunas[3]);
            question.setAnswer3(colunas[4]);
            question.setAnswer4(colunas[5]);
            question.setCorrectAnswer(Byte.parseByte(colunas[6]));
            question.setTimeInSeconds(Integer.parseInt(colunas[7]));
            questionRepository.save(question);
        }
    }

    private void startGame(String titulo, Integer totalQuestoes) {
        Game entity = new Game();
        entity.setTitle(titulo);
        entity.setTotalQuestions(totalQuestoes);
        entity.setActualQuestion(1L);
        repository.save(entity);
    }

    private String validarArquivo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não informado.");
        }
        String nome = file.getOriginalFilename();
        if (nome == null || nome.isEmpty() || !nome.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("O arquivo informado deve ser CSV.");
        }
        return nome;
    }

    private void actualScores() {
        List<PlayerAnswer> lstResposta = playerAnswerRepository.findAll();
        for (PlayerAnswer playerAnswer : lstResposta) {
            Optional<Player> player = playerRepository.findById(playerAnswer.getIdPlayer());
            if (player.isPresent()) {
                if (playerAnswer.getCorrect()) {
                    player.get().setScore(player.get().getScore() + (120 - playerAnswer.getTimeAnswerInSeconds()));
                } else {
                    player.get().setScore(player.get().getScore() - 120);
                }
            }
        }
        // Eliminar Respostas
        playerAnswerRepository.deleteAllInBatch();
    }

    private boolean isFinished() {
        Game entity = repository.findAll().stream().findFirst()
          .orElseThrow(() -> new NoSuchElementException("Jogo não encontrado, verifique os dados!"));
        return (entity.getActualQuestion() >= entity.getTotalQuestions());
    }

    // -----------------------------------------------------
    // CICLO DE VIDA
    // -----------------------------------------------------

    private void cycleWaiting() {
        timeService.notifyAction(60*5, GameStatus.GAME_WAITING);
        timeService.startTimer(60*5, this::cycleOpenQuestion);
    }

    private void cycleOpenQuestion() {
        ResponseQuestionDTO questao = getQuestion(true);
        if (questao != null) {
            timeService.notifyAction(questao.tempoEmSegundos(), GameStatus.QUESTION_STARTED);
            timeService.startTimer(questao.tempoEmSegundos(), this::cyclefinishQuestion);
        }
    }

    private void cyclefinishQuestion() {
        timeService.startTimer(30, this::nextStep);
        timeService.notifyAction(30, GameStatus.SHOW_RANKING);
    }

    private void nextStep() {
        if (this.isFinished()) {
            timeService.notifyAction(0, GameStatus.GAME_FINISHED);
        } else {
            cycleOpenQuestion();
        }
    }
}
