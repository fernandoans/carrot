package com.fernando.carrotback.domain.entity;

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
    private Long ordem;

    private String description;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private Byte correctAnswer;
    private Integer timeInSeconds;
}
