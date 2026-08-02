package com.fernando.carrotback.presentation.controller;

import com.fernando.carrotback.presentation.dto.RequestAnswerDTO;
import com.fernando.carrotback.presentation.dto.RequestPlayerDTO;
import com.fernando.carrotback.presentation.dto.ResponsePlayerDTO;
import com.fernando.carrotback.application.service.PlayerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService service;

    @PostMapping
    public String register(
      @RequestBody @Valid RequestPlayerDTO request,
      Model model) {
        service.criar(request);

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
          .build()
          .toUriString();

        model.addAttribute("joinUrl", baseUrl + "/addPlayer");

        // Retorna o fragmento HTML do lobby
        return "fragments/lobby :: content";
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
