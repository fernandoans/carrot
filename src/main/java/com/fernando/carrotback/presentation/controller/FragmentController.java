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
@RequestMapping("/fragments")
public class FragmentController {

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
          baseUrl + "/join/player"
        );
        return "fragments/lobby :: content";
    }

    @GetMapping("/join/player")
    public String player(Model model) {
        return "fragments/player :: content";
    }

    @GetMapping("/question")
    public String question(Model model) {
        return "fragments/question :: content";
    }

    @GetMapping("/ranking")
    public String ranking(Model model) {
        return "fragments/ranking :: content";
    }

    @GetMapping("/finished")
    public String finished(Model model) {
        return "fragments/finished :: content";
    }
}
