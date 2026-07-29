package com.fernando.carrotback.service;

import com.fernando.carrotback.domain.dto.ResponseGameDTO;
import com.fernando.carrotback.domain.dto.ResponseQuestionDTO;
import com.fernando.carrotback.domain.dto.ResponseRankingDTO;
import com.fernando.carrotback.domain.entity.Game;
import com.fernando.carrotback.domain.entity.Player;
import com.fernando.carrotback.domain.entity.PlayerAnswer;
import com.fernando.carrotback.domain.entity.Question;
import com.fernando.carrotback.domain.repository.GameRepository;
import com.fernando.carrotback.domain.repository.PlayerAnswerRepository;
import com.fernando.carrotback.domain.repository.PlayerRepository;
import com.fernando.carrotback.domain.repository.QuestionRepository;
import com.fernando.carrotback.enums.GameStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
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

    public ResponseQuestionDTO openQuestion() {
        Game entity = repository.findAll().stream().findFirst()
          .orElseThrow(() -> new NoSuchElementException("Jogo não encontrado, verifique os dados!"));
        entity.setStarted(true);
        entity.setStatus(GameStatus.QUESTION_STARTED);
        Question question = questionRepository.findById(entity.getActualQuestion())
          .orElseThrow(() -> new NoSuchElementException("Questão não encontrada, verifique os dados!"));
        this.toResponse(repository.save(entity));
        return toResponse(question);
    }

    public List<ResponseRankingDTO> finishQuestion() {
        Game entity = repository.findAll().stream().findFirst()
          .orElseThrow(() -> new NoSuchElementException("Jogo não encontrado, verifique os dados!"));
        entity.setActualQuestion(entity.getActualQuestion() + 1);
        entity.setStatus(GameStatus.QUESTION_ENDED);
        this.toResponse(repository.save(entity));
        // Atualizar scores dos jogadores
        actualScores();
        return playerRepository.findTop20ByOrderByScoreDesc()
          .stream()
          .map(this::toResponse)
          .toList();
    }

    public Boolean isFinished() {
        Game entity = repository.findAll().stream().findFirst()
          .orElseThrow(() -> new NoSuchElementException("Jogo não encontrado, verifique os dados!"));
        return (entity.getActualQuestion() >= entity.getTotalQuestions());
    }

    @Transactional
    public int processFileCsv(MultipartFile file) {
        int totalQuestoes = 0;
        String nome = this.validarArquivo(file);
        // Limpar toda a base
        clearDatabase();
        // Carregar o arquivo CSV
        try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String linha = reader.readLine(); // Retira a linha do cabeçalho
            while ((linha = reader.readLine()) != null) {
                saveQuestion(linha.split(";"));
                totalQuestoes++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar o arquivo!", e);
        }
        // Carregar o Game
        if (totalQuestoes == 0) {
            throw new RuntimeException("Nenhuma questão processada, verifique o arquivo!");
        }
        this.startGame(nome, totalQuestoes);
        return totalQuestoes;
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
            question.setOrdem(Long.parseLong(colunas[0]));
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
        try {
            Game entity = new Game();
            entity.setTitle(titulo);
            entity.setPin(this.gerarPin());
            entity.setStarted(true);
            entity.setFinished(false);
            entity.setTotalQuestions(totalQuestoes);
            entity.setStatus(GameStatus.GAME_WAITING);
            entity.setActualQuestion(0L);
            repository.save(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
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

    private ResponseGameDTO toResponse(Game entity) {
        return new ResponseGameDTO(
          entity.getId(),
          entity.getTitle(),
          entity.getPin(),
          entity.getStatus().getMensagem()
        );
    }

    private ResponseQuestionDTO toResponse(Question entity) {
        return new ResponseQuestionDTO(
          entity.getDescription(),
          entity.getAnswer1(),
          entity.getAnswer2(),
          entity.getAnswer3(),
          entity.getAnswer4(),
          entity.getTimeInSeconds(),
          entity.getCorrectAnswer()
        );
    }

    private ResponseRankingDTO toResponse(Player entity) {
        return new ResponseRankingDTO(
          entity.getNickname(),
          entity.getScore()
        );
    }

    private String gerarPin() {
        SecureRandom random = new SecureRandom();
        return String.valueOf(
          100000 + random.nextInt(900000));
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
        playerAnswerRepository.deleteAll();
    }
}
