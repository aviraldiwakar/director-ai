package com.aicreator.directorai.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Inbound payload for kicking off a new production job.
 */
public record CreateJobRequest(

        @NotBlank(message = "title is required")
        String title,

        @NotBlank(message = "theme is required")
        String theme
) {
}
