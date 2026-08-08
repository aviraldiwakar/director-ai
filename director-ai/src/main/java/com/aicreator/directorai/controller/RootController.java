package com.aicreator.directorai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String home() {
        return "🤖 Autonomous Tech Persona Agent is Live! \n\nEvaluator endpoints /api/agent/init and /api/agent/feed are ready for automated testing.";
    }
}