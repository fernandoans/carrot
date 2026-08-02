package com.fernando.carrotback.infrastructure.enums;

public enum GameStatus {
    TEST("Testar a conexão"),
    NOT_STARTED("Não iniciado"),
    GAME_WAITING("Aguardando Jogadores"),
    PLAYER_JOINED("Você se juntou ao jogo"),
    QUESTION_STARTED("Questão Iniciada"),
    SHOW_RANKING("Mostrar Ranking"),
    GAME_FINISHED("Finalizado");

    private final String mensagem;

    private GameStatus(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
