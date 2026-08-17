package com.fernando.carrotback.presentation.controller;

import com.fernando.carrotback.application.service.GameService;
import com.fernando.carrotback.application.service.PlayerService;
import com.fernando.carrotback.presentation.dto.ResponseQuestionDTO;
import com.fernando.carrotback.presentation.dto.ResponseRankingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/frg/screen")
public class FragmentScreenController {

    private final PlayerService playerService;
    private final GameService gameService;

    @GetMapping("/lobby")
    public String lobby(Model model) {
        model.addAttribute(
            "players",
            playerService.listar()
        );
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
          .build()
          .toUriString();
        model.addAttribute(
          "joinUrl",
          baseUrl + "/player/join"
        );
        return "fragments/screen/lobby :: content";
    }

    @GetMapping("/players")
    public String players(Model model) {
        model.addAttribute(
          "players",
          playerService.listar()
        );
        return "fragments/screen/lobby :: player-list";
    }

    @GetMapping("/question")
    public String question(Model model) {
        ResponseQuestionDTO questionDto = gameService.getQuestion(true);
        model.addAttribute("question", questionDto);
        model.addAttribute("totalQuestions", gameService.getTotalQuestions());

        return "fragments/screen/question :: content";
    }

    @GetMapping("/ranking")
    public String ranking(Model model) {
        List<ResponseRankingDTO> lstRanking = gameService.getRanking();
        model.addAttribute("players", lstRanking);
        return "fragments/screen/ranking :: content";
    }

    @GetMapping("/finished")
    public String finished(Model model) {
        List<ResponseRankingDTO> lstRanking = gameService.getRanking();
        model.addAttribute("players", lstRanking);
        return "fragments/screen/finished :: content";
    }
}
