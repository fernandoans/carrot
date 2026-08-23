package com.fernando.carrotback.presentation.dto;

public record ResponseMessageDTO(
    String type,
    String content,
    Integer tempo
) {
}
