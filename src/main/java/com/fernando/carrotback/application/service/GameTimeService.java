package com.fernando.carrotback.application.service;

import com.fernando.carrotback.infrastructure.enums.GameStatus;
import com.fernando.carrotback.presentation.dto.ResponseMessageDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class GameTimeService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    // Referência da tarefa atual e controle de tempo
    private ScheduledFuture<?> currentTask;
    private Instant startTime;
    private int totalDurationSeconds = 0;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    public GameTimeService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Inicia o cronômetro para uma etapa do jogo.
     *
     * @param seconds Duração da etapa em segundos
     * @param callback Ação executada ao término do tempo
     */
    public synchronized void startTimer(int seconds, Runnable callback) {
        // Se já houver um temporizador rodando, cancela para iniciar a nova fase
        stopTimer();

        this.totalDurationSeconds = seconds;
        this.startTime = Instant.now();
        this.isRunning.set(true);

        // Agenda a execução e guarda o ScheduledFuture
        this.currentTask = scheduler.schedule(() -> {
            this.isRunning.set(false);
            callback.run();
        }, seconds, TimeUnit.SECONDS);
    }

    /**
     * Retorna quantos segundos JÁ SE PASSARAM desde o início da fase atual.
     */
    public long getElapsedSeconds() {
        if (!isRunning.get() || startTime == null) {
            return 0;
        }
        long elapsed = Instant.now().getEpochSecond() - startTime.getEpochSecond();
        // Garante que não retorne um valor superior à duração total por pequeno atraso na Thread
        return Math.min(elapsed, totalDurationSeconds);
    }

    /**
     * Interrompe/cancela o temporizador atual (caso o professor pule a questão, por exemplo).
     */
    public synchronized void stopTimer() {
        if (currentTask != null && !currentTask.isDone()) {
            currentTask.cancel(true);
        }
        this.isRunning.set(false);
    }

    /**
     * Notifica o projetor e os celulares via STOMP sobre a ação/troca de estado.
     */
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
