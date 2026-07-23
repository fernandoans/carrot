package com.fernando.carrotback.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestCreateGameDTO(
  @NotBlank(message = "O título é obrigatório!")
  String titulo
) {
}
