package com.fernando.carrotback.presentation.dto;

import com.fernando.carrotback.domain.model.Question;

public record ResponseQuestionDTO(
  Long ordem,
  String descricao,
  String pergunta1,
  String pergunta2,
  String pergunta3,
  String pergunta4,
  Integer tempoEmSegundos,
  Byte correta
) {
    public static ResponseQuestionDTO toResponse(Question entity) {
        return new ResponseQuestionDTO(
          entity.getOrder(),
          entity.getDescription(),
          entity.getAnswer1(),
          entity.getAnswer2(),
          entity.getAnswer3(),
          entity.getAnswer4(),
          entity.getTimeInSeconds(),
          entity.getCorrectAnswer()
        );
    }

}
