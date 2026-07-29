package com.fernando.carrotback.controller;

import com.fernando.carrotback.domain.dto.*;
import com.fernando.carrotback.enums.GameStatus;
import com.fernando.carrotback.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService service;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadCsv(
      @RequestParam("file") MultipartFile file
    ) {
        int totQuestoes = service.processFileCsv(file);
        if (totQuestoes > 0) {
            messagingTemplate.convertAndSend(
              "/topic/game",
              new ResponseMessageDTO(
                GameStatus.GAME_WAITING.toString(),
                GameStatus.GAME_WAITING.getMensagem(),
                60*5 // 5 minutos
              )
            );
        }
        return ResponseEntity.ok("Arquivo carregado com " + totQuestoes + " questões.");
    }

    @GetMapping("/open-question")
    public ResponseEntity<ResponseQuestionDTO> openQuestion() {
        return ResponseEntity.ofNullable(service.openQuestion());
    }

    @GetMapping("/finish-question")
    public ResponseEntity<List<ResponseRankingDTO>> finishQuestion() {
        return ResponseEntity.ofNullable(service.finishQuestion());
    }

    @GetMapping("/finish")
    public ResponseEntity<Boolean> finishGame() {
        return ResponseEntity.ofNullable(service.isFinished());
    }
}
