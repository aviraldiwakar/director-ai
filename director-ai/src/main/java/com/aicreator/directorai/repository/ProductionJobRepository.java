package com.aicreator.directorai.repository;

import com.aicreator.directorai.entity.ProductionJob;
import com.aicreator.directorai.model.ProductionStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository for {@link ProductionJob} persistence.
 * Provides standard CRUD via JpaRepository plus a stage-based lookup
 * used by the orchestrator to find jobs sitting at a given pipeline stage.
 */
@Repository
public interface ProductionJobRepository extends JpaRepository<ProductionJob, UUID> {

    List<ProductionJob> findByCurrentStage(ProductionStage currentStage);
}
