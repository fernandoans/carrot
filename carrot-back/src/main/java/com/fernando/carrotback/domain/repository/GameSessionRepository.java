package com.fernando.carrotback.domain.repository;

import com.fernando.carrotback.domain.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameSessionRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByPin(String pin);
}
