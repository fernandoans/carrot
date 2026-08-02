package com.fernando.carrotback.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestAnswerDTO(
  @NotBlank(message = "ID do Jogador é obrigatório!")
  Long idJogador,
  Byte resposta
) {
}
