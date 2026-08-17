package com.fernando.carrotback.presentation.controller;

import com.fernando.carrotback.application.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService service;

    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadCsv(
      @RequestParam("file") MultipartFile file
    ) {
        int totalQuestoes = service.processFileCsv(file);
        // Retornar uma mensagem HTML dentro da div #resultado-upload:
        String htmlResposta = """
            <div class="alert alert-success mt-3" role="alert">
                Arquivo enviado com sucesso! Total de %d questões carregadas.
            </div>
            """.formatted(totalQuestoes);

        return ResponseEntity.ok(htmlResposta);
    }
}