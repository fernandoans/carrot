package com.fernando.carrotback.domain.dto;

public record ResponseMessageDTO(
    String type,
    String content,
    Integer tempo
) {
}
