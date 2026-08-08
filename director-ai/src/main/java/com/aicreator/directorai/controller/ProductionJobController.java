package com.aicreator.directorai.controller;

import com.aicreator.directorai.dto.AdvanceJobRequest;
import com.aicreator.directorai.dto.CreateJobRequest;
import com.aicreator.directorai.dto.JobStatusResponse;
import com.aicreator.directorai.service.ProductionJobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST API surface for Project Director AI: creating production jobs,
 * inspecting their status, and advancing them through the pipeline.
 */
@RestController
@RequestMapping("/api/v1/jobs")
@CrossOrigin
@RequiredArgsConstructor
public class ProductionJobController {

    private final ProductionJobService productionJobService;

    @PostMapping
    public ResponseEntity<JobStatusResponse> createJob(@Valid @RequestBody CreateJobRequest request) {
        JobStatusResponse created = productionJobService.createJob(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobStatusResponse> getJobById(@PathVariable UUID id) {
        return ResponseEntity.ok(productionJobService.getJobById(id));
    }

    @GetMapping
    public ResponseEntity<List<JobStatusResponse>> getAllJobs() {
        return ResponseEntity.ok(productionJobService.getAllJobs());
    }

    @PatchMapping("/{id}/advance")
    public ResponseEntity<JobStatusResponse> advanceJob(@PathVariable UUID id,
                                                          @Valid @RequestBody AdvanceJobRequest request) {
        JobStatusResponse updated = productionJobService.updateJobStageAndArtifacts(
                id,
                request.newStage(),
                request.script(),
                request.shotList(),
                request.thumbnailPrompt()
        );
        return ResponseEntity.ok(updated);
    }
}
