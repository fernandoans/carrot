package com.fernando.carrotback.controller;

import com.fernando.carrotback.domain.dto.RequestCreateGameDTO;
import com.fernando.carrotback.domain.dto.ResponseGameDTO;
import com.fernando.carrotback.domain.dto.ResponseQuestionDTO;
import com.fernando.carrotback.domain.dto.ResponseRankingDTO;
import com.fernando.carrotback.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService service;

    @PostMapping("/start")
    public ResponseEntity<ResponseGameDTO> startGame(@RequestBody @Valid RequestCreateGameDTO request) {
        return ResponseEntity.ofNullable(service.startGame(request.titulo()));
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
