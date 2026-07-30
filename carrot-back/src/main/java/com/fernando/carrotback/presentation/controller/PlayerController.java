package com.fernando.carrotback.presentation.controller;

import com.fernando.carrotback.presentation.dto.RequestAnswerDTO;
import com.fernando.carrotback.presentation.dto.RequestPlayerDTO;
import com.fernando.carrotback.presentation.dto.ResponsePlayerDTO;
import com.fernando.carrotback.application.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService service;

    @PostMapping
    public ResponseEntity<ResponsePlayerDTO> register(@RequestBody @Valid RequestPlayerDTO request) {
        return ResponseEntity.ofNullable(service.criar(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponsePlayerDTO>> getPlayers() {
        return ResponseEntity.ofNullable(service.listar());
    }

    @PostMapping("/answer")
    public ResponseEntity<Boolean> sendAwnser(@RequestBody @Valid RequestAnswerDTO request) {
        return ResponseEntity.ofNullable(service.sendAnswer(request));
    }
}
