package com.fernando.carrotback.domain.repository;

import com.fernando.carrotback.domain.model.PlayerAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerAnswerRepository extends JpaRepository<PlayerAnswer, Long> {
}
