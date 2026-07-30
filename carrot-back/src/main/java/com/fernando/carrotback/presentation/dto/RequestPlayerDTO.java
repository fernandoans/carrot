package com.fernando.carrotback.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestPlayerDTO(
  @NotBlank(message = "O nome é obrigatório!")
  String nome
) { }
