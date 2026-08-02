package com.fernando.carrotback.application.service;

import com.fernando.carrotback.domain.repository.GameRepository;
import com.fernando.carrotback.presentation.dto.RequestAnswerDTO;
import com.fernando.carrotback.presentation.dto.RequestPlayerDTO;
import com.fernando.carrotback.presentation.dto.ResponsePlayerDTO;
import com.fernando.carrotback.domain.model.Player;
import com.fernando.carrotback.domain.model.PlayerAnswer;
import com.fernando.carrotback.domain.repository.PlayerAnswerRepository;
import com.fernando.carrotback.domain.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository repository;
    private final PlayerAnswerRepository playerAnswerRepository;
    private final GameService gameService;
    private final GameTimeService gameTimeService;

    public ResponsePlayerDTO criar(RequestPlayerDTO player) {
        Player entity = new Player();
        entity.setNickname(player.nickname());
        entity.setScore(0L);
        return ResponsePlayerDTO.toResponse(repository.save(entity));
    }

    public List<ResponsePlayerDTO> listar() {
        return repository.findAll()
          .stream()
          .map(ResponsePlayerDTO::toResponse)
          .toList();
    }

    public Boolean sendAnswer(RequestAnswerDTO dto) {
        Player entity = repository.findById(dto.idJogador())
          .orElseThrow(() -> new NoSuchElementException("Jogador não encontrado"));
        try {
            PlayerAnswer answer = new PlayerAnswer();
            answer.setIdPlayer(entity.getId());
            answer.setAnswer(dto.resposta());
            answer.setCorrect(dto.resposta().equals(gameService.getCorrectAnswer()));
            answer.setTimeAnswerInSeconds(gameTimeService.getElapsedSeconds());
            playerAnswerRepository.save(answer);
            return Boolean.TRUE;
        } catch (IllegalArgumentException e) {
            return Boolean.FALSE;
        }
    }
}
