package com.fernando.carrotback.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoutesController {

    @GetMapping("/admin/upload")
    public String uploadPage() {
        return "upload";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
