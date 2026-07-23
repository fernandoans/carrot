package com.fernando.carrotback.domain.dto;

public record ResponseGameDTO(
  Long id,
  String titulo,
  String pin,
  String status
) {
}
