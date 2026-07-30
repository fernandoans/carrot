package com.fernando.carrotback.enums;

public enum GameStatus {
    TEST("Testar a conexão"),
    GAME_WAITING("Aguardando Jogadores"),
    PLAYER_JOINED("Você se juntou ao jogo"),
    QUESTION_STARTED("Questão Iniciada"),
    QUESTION_ENDED("Questão Finalizada"),
    RANKING_UPDATED("Ranking Atualizado"),
    GAME_FINISHED("Finalizado");

    private String mensagem;

    private GameStatus(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
