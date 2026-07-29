package com.fernando.carrotback.domain.entity;

import com.fernando.carrotback.enums.GameStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String pin;
    private Boolean started;
    private Boolean finished;
    @Enumerated(EnumType.STRING)
    private GameStatus status;
    private Long actualQuestion;
    private Integer totalQuestions;
}
