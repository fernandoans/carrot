package com.fernando.carrotback.enums;

public enum GameStatus {
    WAITING("Aguardando Jogadores"),
    QUESTION_STARTED("Questão Iniciada"),
    QUESTION_FINISH("Questão Finalizada"),
    RANKING_UPDATE("Ranking Atualizado"),
    FINISHED("Finalizado");

    private String nome;

    private GameStatus(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
