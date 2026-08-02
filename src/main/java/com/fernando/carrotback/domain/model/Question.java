package com.fernando.carrotback.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @Column(name = "question_order")
    private Long order;

    private String description;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private Byte correctAnswer;
    private Integer timeInSeconds;
}
