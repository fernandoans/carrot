package com.fernando.carrotback.controller;

import com.fernando.carrotback.domain.dto.ResponseMessageDTO;
import com.fernando.carrotback.enums.GameStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/send")
    public ResponseEntity<String> send() {
        messagingTemplate.convertAndSend(
          "/topic/game",
          new ResponseMessageDTO(
            GameStatus.TEST.toString(),
            GameStatus.TEST.getMensagem(),
            60
          )
        );
        return ResponseEntity.ok("Mensagem Enviada!");
    }
}