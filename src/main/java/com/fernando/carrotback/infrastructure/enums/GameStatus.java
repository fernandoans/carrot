package com.fernando.carrotback.infrastructure.enums;

import lombok.Getter;

public enum GameStatus {
    TEST("Testar a conexão"),
    NOT_STARTED("Não iniciado"),
    GAME_WAITING("Aguardando Jogadores"),
    PLAYER_JOINED("Você se juntou ao jogo"),
    QUESTION_STARTED("Questão Iniciada"),
    SHOW_RANKING("Mostrar Ranking"),
    GAME_FINISHED("Finalizado");

    @Getter
    private final String mensagem;

    GameStatus(String mensagem) {
        this.mensagem = mensagem;
    }
}
