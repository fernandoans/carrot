package com.fernando.carrotback.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestPlayerDTO(
  @NotBlank(message = "Nickname é obrigatório!")
  String nickname
) { }
