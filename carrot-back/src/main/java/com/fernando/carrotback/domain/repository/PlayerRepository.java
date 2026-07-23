package com.fernando.carrotback.domain.repository;

import com.fernando.carrotback.domain.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findTop20ByOrderByScoreDesc();
}
