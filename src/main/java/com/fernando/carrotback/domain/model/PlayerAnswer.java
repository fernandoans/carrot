package com.fernando.carrotback.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerAnswer {
    @Id
    private Long idPlayer;

    private Long idQuestion;
    private Byte answer;
    private Integer timeAnswerInSeconds;
    private Boolean correct;
}
