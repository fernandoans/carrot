package com.fernando.carrotback.presentation.dto;

import com.fernando.carrotback.domain.model.Player;

public record ResponsePlayerDTO(
  Long id,
  String nickname,
  Long pontuacao
) {
    public static ResponsePlayerDTO toResponse(Player entity) {
        return new ResponsePlayerDTO(
          entity.getId(),
          entity.getNickname(),
          entity.getScore()
        );
    }

}
