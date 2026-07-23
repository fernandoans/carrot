package com.fernando.carrotback.service;

import com.fernando.carrotback.domain.dto.RequestAnswerDTO;
import com.fernando.carrotback.domain.dto.RequestPlayerDTO;
import com.fernando.carrotback.domain.dto.ResponsePlayerDTO;
import com.fernando.carrotback.domain.entity.Player;
import com.fernando.carrotback.domain.entity.PlayerAnswer;
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

    public ResponsePlayerDTO criar(RequestPlayerDTO player) {
        Player entity = new Player();
        entity.setNickname(player.nome());
        entity.setScore(0);
        return this.toResponse(repository.save(entity));
    }

    public List<ResponsePlayerDTO> listar() {
        return repository.findAll()
          .stream()
          .map(this::toResponse)
          .toList();
    }

    public Boolean sendAwnser(RequestAnswerDTO dto) {
        Player entity = repository.findById(dto.idJogador())
          .orElseThrow(() -> new NoSuchElementException("Jogador não encontrado"));

        PlayerAnswer answer = new PlayerAnswer();
        answer.setIdPlayer(entity.getId());
        answer.setAnswer(dto.resposta());
        answer.setCorrect(dto.correta());
        answer.setTimeAnswerInSeconds(dto.tempo());
        playerAnswerRepository.save(answer);
        return Boolean.TRUE;
    }

    private ResponsePlayerDTO toResponse(Player entity) {
        return new ResponsePlayerDTO(
          entity.getId(),
          entity.getNickname(),
          entity.getScore()
        );
    }
}
