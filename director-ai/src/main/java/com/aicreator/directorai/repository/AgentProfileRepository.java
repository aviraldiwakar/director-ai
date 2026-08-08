package com.aicreator.directorai.repository;

import com.aicreator.directorai.entity.AgentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgentProfileRepository extends JpaRepository<AgentProfile, UUID> {
}
