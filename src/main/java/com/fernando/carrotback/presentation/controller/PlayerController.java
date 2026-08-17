package com.fernando.carrotback.presentation.controller;

import com.fernando.carrotback.application.service.GameService;
import com.fernando.carrotback.infrastructure.enums.GameStatus;
import com.fernando.carrotback.presentation.dto.RequestAnswerDTO;
import com.fernando.carrotback.presentation.dto.RequestPlayerDTO;
import com.fernando.carrotback.presentation.dto.ResponsePlayerDTO;
import com.fernando.carrotback.application.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService service;
    private final GameService gameService;

    @GetMapping("/join")
    public String joinPlayer(
      @RequestHeader(value = "HX-Request", required = false) boolean isHtmx,
      Model model
    ) {
        GameStatus currentState = gameService.getCurrentState();

        // Define qual fragmento deve ser carregado dentro do layout do jogador
        String fragmentPath;
        if (currentState == GameStatus.GAME_WAITING) {
            fragmentPath = "fragments/player/join";
        } else {
            // Se o jogo já estiver em andamento (QUESTION_STARTED, SHOW_RANKING, etc.)
            fragmentPath = "fragments/player/default";
        }
        if (isHtmx) {
            return fragmentPath + " :: content";
        }
        model.addAttribute("content", fragmentPath);
        return "player"; // Retorna templates/player.html (a casca pai)
    }

    @PostMapping
    public String register(
      @RequestBody @Valid RequestPlayerDTO request,
      HttpSession session,
      Model model
    ) {
        ResponsePlayerDTO player = service.criar(request);
        session.setAttribute("PLAYER_ID", player.id());
        // Retorna o fragmento HTML para esperar iniciar
        model.addAttribute("nickname", player.nickname());
        return "fragments/player/waiting :: content";
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<ResponsePlayerDTO>> getPlayers() {
        return ResponseEntity.ofNullable(service.listar());
    }

    @PostMapping("/answer")
    public ResponseEntity<String> sendAnswer(@RequestBody @Valid RequestAnswerDTO request) {
        boolean success = service.sendAnswer(request);
        if (success) {
            String htmlFeedback = """
            <div class="alert alert-success mt-2 text-center role="alert">
                <h5 class="fw-bold mb-0">Resposta Registrada! 🚀</h5>
                <small>Aguarde a apuração na tela principal.</small>
            </div>
            """;
            return ResponseEntity.ok(htmlFeedback);
        }
        return ResponseEntity.badRequest().body("<div class='alert alert-danger'>Erro ao enviar resposta.</div>");
    }
}
