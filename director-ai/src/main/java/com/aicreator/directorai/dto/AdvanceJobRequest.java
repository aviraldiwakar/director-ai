package com.aicreator.directorai.dto;

import com.aicreator.directorai.model.ProductionStage;
import jakarta.validation.constraints.NotNull;

/**
 * Inbound payload for advancing a production job: the stage it should
 * move to, plus whichever pipeline artifacts were produced along the way.
 * Artifact fields are intentionally optional (unlike newStage) so a
 * caller can advance the stage without having every artifact in hand yet.
 */
public record AdvanceJobRequest(

        @NotNull(message = "newStage is required")
        ProductionStage newStage,

        String script,
        String shotList,
        String thumbnailPrompt
) {
}
