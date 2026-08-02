package com.fernando.carrotback.presentation.controller;

import com.fernando.carrotback.presentation.dto.ResponseQuestionDTO;
import com.fernando.carrotback.presentation.dto.ResponseRankingDTO;
import com.fernando.carrotback.application.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService service;

    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadCsv(
      @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(
          "Arquivo carregado com " + service.processFileCsv(file) + " questões.");
    }

    @GetMapping("/question")
    public ResponseEntity<ResponseQuestionDTO> getQuestion() {
        return ResponseEntity.ofNullable(service.getQuestion(false));
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<ResponseRankingDTO>> getRanking() {
        return ResponseEntity.ofNullable(service.getRanking());
    }
}
