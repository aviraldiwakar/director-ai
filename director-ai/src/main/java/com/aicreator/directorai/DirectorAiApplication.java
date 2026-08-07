package com.aicreator.directorai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for Project Director AI.
 *
 * This is the core orchestrator service responsible for coordinating
 * the autonomous cinematic production pipeline (state management,
 * task scheduling, and downstream service orchestration).
 */
@SpringBootApplication
public class DirectorAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DirectorAiApplication.class, args);
    }
}
