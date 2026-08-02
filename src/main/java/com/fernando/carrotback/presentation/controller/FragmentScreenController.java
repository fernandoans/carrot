package com.fernando.carrotback.presentation.controller;

import com.fernando.carrotback.application.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/frg/screen")
public class FragmentScreenController {

    private final PlayerService playerService;

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
        return "fragments/screen/question :: content";
    }

    @GetMapping("/ranking")
    public String ranking(Model model) {
        return "fragments/screen/ranking :: content";
    }

    @GetMapping("/finished")
    public String finished(Model model) {
        return "fragments/screen/finished :: content";
    }
}
