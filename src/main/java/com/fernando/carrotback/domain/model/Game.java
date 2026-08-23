package com.fernando.carrotback.domain.model;

import com.fernando.carrotback.infrastructure.enums.GameStatus;
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
    private Long actualQuestion;
    private Integer totalQuestions;
    @Enumerated(EnumType.STRING)
    private GameStatus status;
    private Byte correctAnswer;
}
