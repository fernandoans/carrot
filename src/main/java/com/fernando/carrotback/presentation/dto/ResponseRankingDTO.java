package com.fernando.carrotback.presentation.dto;

import com.fernando.carrotback.domain.model.Player;

public record ResponseRankingDTO(
  String nickname,
  Long pontos
) {
    public static ResponseRankingDTO toResponse(Player entity) {
        return new ResponseRankingDTO(
          entity.getNickname(),
          entity.getScore()
        );
    }
}
