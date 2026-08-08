package com.aicreator.directorai.service;

import com.aicreator.directorai.dto.CreateJobRequest;
import com.aicreator.directorai.dto.JobStatusResponse;
import com.aicreator.directorai.entity.ProductionJob;
import com.aicreator.directorai.exception.ResourceNotFoundException;
import com.aicreator.directorai.model.ProductionStage;
import com.aicreator.directorai.repository.ProductionJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Business logic for creating production jobs and driving them through
 * the pipeline. Accepts and returns DTOs at its boundary so the
 * controller layer never has to touch {@link ProductionJob} directly.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductionJobService {

    private final ProductionJobRepository productionJobRepository;

    public JobStatusResponse createJob(CreateJobRequest request) {
        ProductionJob job = ProductionJob.builder()
                .title(request.title())
                .theme(request.theme())
                .currentStage(ProductionStage.INITIATED)
                .build();

        return JobStatusResponse.fromEntity(productionJobRepository.save(job));
    }

    @Transactional(readOnly = true)
    public JobStatusResponse getJobById(UUID id) {
        return JobStatusResponse.fromEntity(findJobOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<JobStatusResponse> getAllJobs() {
        return productionJobRepository.findAll().stream()
                .map(JobStatusResponse::fromEntity)
                .toList();
    }

    /**
     * Applies a stage transition and/or newly generated artifacts to an
     * existing job. Each artifact parameter is optional (null = leave
     * unchanged), so a caller can advance the stage without necessarily
     * supplying every artifact in the same call.
     */
    public JobStatusResponse updateJobStageAndArtifacts(UUID id,
                                                          ProductionStage newStage,
                                                          String script,
                                                          String shotList,
                                                          String thumbnailPrompt) {
        ProductionJob job = findJobOrThrow(id);

        if (newStage != null) {
            job.setCurrentStage(newStage);
        }
        if (script != null) {
            job.setGeneratedScript(script);
        }
        if (shotList != null) {
            job.setShotListJson(shotList);
        }
        if (thumbnailPrompt != null) {
            job.setThumbnailPrompt(thumbnailPrompt);
        }

        return JobStatusResponse.fromEntity(productionJobRepository.save(job));
    }

    private ProductionJob findJobOrThrow(UUID id) {
        return productionJobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ProductionJob not found with id: " + id));
    }
}
