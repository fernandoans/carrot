package com.fernando.carrotback.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestAnswerDTO(
  @NotBlank(message = "ID do Jogador é obrigatório!")
  Long idJogador,
  @NotBlank(message = "Se correta é obrigatória!")
  Boolean correta,
  Byte resposta,
  @NotBlank(message = "Tempo é obrigatório!")
  Integer tempo
) {
}
