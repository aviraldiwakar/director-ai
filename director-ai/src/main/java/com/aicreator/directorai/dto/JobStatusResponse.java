package com.aicreator.directorai.dto;

import com.aicreator.directorai.entity.ProductionJob;
import com.aicreator.directorai.model.ProductionStage;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Outbound view of a production job's current status and whatever
 * artifacts the pipeline has generated for it so far.
 */
public record JobStatusResponse(
        UUID id,
        String title,
        String theme,
        ProductionStage currentStage,
        String generatedScript,
        String shotListJson,
        String thumbnailPrompt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static JobStatusResponse fromEntity(ProductionJob job) {
        return new JobStatusResponse(
                job.getId(),
                job.getTitle(),
                job.getTheme(),
                job.getCurrentStage(),
                job.getGeneratedScript(),
                job.getShotListJson(),
                job.getThumbnailPrompt(),
                job.getCreatedAt(),
                job.getUpdatedAt()
        );
    }
}
