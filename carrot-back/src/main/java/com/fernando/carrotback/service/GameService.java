package com.fernando.carrotback.service;

import com.fernando.carrotback.domain.dto.ResponseGameDTO;
import com.fernando.carrotback.domain.dto.ResponseQuestionDTO;
import com.fernando.carrotback.domain.dto.ResponseRankingDTO;
import com.fernando.carrotback.domain.entity.Game;
import com.fernando.carrotback.domain.entity.Player;
import com.fernando.carrotback.domain.entity.PlayerAnswer;
import com.fernando.carrotback.domain.entity.Question;
import com.fernando.carrotback.domain.repository.GameSessionRepository;
import com.fernando.carrotback.domain.repository.PlayerAnswerRepository;
import com.fernando.carrotback.domain.repository.PlayerRepository;
import com.fernando.carrotback.domain.repository.QuestionRepository;
import com.fernando.carrotback.enums.GameStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameSessionRepository repository;
    private final QuestionRepository questionRepository;
    private final PlayerRepository playerRepository;
    private final PlayerAnswerRepository playerAnswerRepository;

    public ResponseGameDTO startGame(String titulo) {
        Long totalQuestoes = questionRepository.count();
        if (totalQuestoes == 0) {
            throw new NoSuchElementException("Não foi encontrada questão disponível");
        }
        Game entity = new Game();
        entity.setTitle(titulo);
        entity.setPin(this.gerarPin());
        entity.setStarted(false);
        entity.setFinished(false);
        entity.setActualQuestion(1L);
        entity.setTotalQuestions(totalQuestoes);
        entity.setStatus(GameStatus.WAITING);
        return this.toResponse(repository.save(entity));
    }

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
        entity.setStatus(GameStatus.QUESTION_FINISH);
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

    private ResponseGameDTO toResponse(Game entity) {
        return new ResponseGameDTO(
          entity.getId(),
          entity.getTitle(),
          entity.getPin(),
          entity.getStatus().getNome()
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
