package com.aicreator.directorai.repository;

import com.aicreator.directorai.entity.FeedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedPostRepository extends JpaRepository<FeedPost, UUID> {
    List<FeedPost> findByAgentIdOrderByCreatedAtDesc(UUID agentId);
}