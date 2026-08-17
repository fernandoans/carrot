package com.fernando.carrotback.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestAnswerDTO(
  @NotNull(message = "ID do Jogador é obrigatório!")
  Long idJogador,
  @NotNull(message = "A opção escolhida é obrigatória")
  Byte resposta
) {
}
