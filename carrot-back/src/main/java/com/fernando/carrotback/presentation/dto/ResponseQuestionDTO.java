package com.fernando.carrotback.domain.dto;

public record ResponseQuestionDTO(
  String descricao,
  String pergunta1,
  String pergunta2,
  String pergunta3,
  String pergunta4,
  Integer tempoEmSegundos,
  Byte correta
) {
}
