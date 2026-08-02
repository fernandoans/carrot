package com.fernando.carrotback.application.service;

import com.fernando.carrotback.infrastructure.enums.GameStatus;
import com.fernando.carrotback.presentation.dto.ResponseMessageDTO;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class GameTimeService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void startTimer(int seconds, Runnable callback) {
        scheduler.schedule(
          callback,
          seconds,
          TimeUnit.SECONDS
        );
    }

    public void notifyAction(int seconds, GameStatus gameStatus) {
        messagingTemplate.convertAndSend(
          "/topic/game",
          new ResponseMessageDTO(
            gameStatus.toString(),
            gameStatus.getMensagem(),
            seconds
          )
        );
    }
}
