package com.fernando.carrotback.presentation.controller;

import com.fernando.carrotback.application.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/frg/player")
public class FragmentPlayerController {

    @GetMapping("/question")
    public String question(Model model) {
        return "fragments/player/question :: content";
    }

    @GetMapping("/ranking")
    public String ranking(Model model) {
        return "fragments/player/ranking :: content";
    }

    @GetMapping("/finished")
    public String finished(Model model) {
        return "fragments/player/finished :: content";
    }
}
