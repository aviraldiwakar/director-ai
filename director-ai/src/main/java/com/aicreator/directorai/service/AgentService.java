package com.aicreator.directorai.service;

import com.aicreator.directorai.dto.CreatePostRequest;
import com.aicreator.directorai.dto.FeedPostDto;
import com.aicreator.directorai.dto.FeedResponse;
import com.aicreator.directorai.dto.InitAgentRequest;
import com.aicreator.directorai.dto.InitAgentResponse;
import com.aicreator.directorai.entity.AgentProfile;
import com.aicreator.directorai.entity.FeedPost;
import com.aicreator.directorai.exception.ResourceNotFoundException;
import com.aicreator.directorai.repository.AgentProfileRepository;
import com.aicreator.directorai.repository.FeedPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AgentService {

    private final AgentProfileRepository agentProfileRepository;
    private final FeedPostRepository feedPostRepository;

    public InitAgentResponse initAgent(InitAgentRequest request) {
        AgentProfile profile = AgentProfile.builder()
                .name(request.persona().name())
                .domain(request.persona().domain())
                .build();
        AgentProfile saved = agentProfileRepository.save(profile);
        return new InitAgentResponse(saved.getId().toString());
    }

    @Transactional(readOnly = true)
    public FeedResponse getFeed(String agentIdStr) {
        UUID agentId = parseUUID(agentIdStr);
        List<FeedPostDto> posts = feedPostRepository.findByAgentIdOrderByCreatedAtDesc(agentId)
                .stream()
                .map(FeedPostDto::fromEntity)
                .toList();
        return new FeedResponse(posts);
    }

    public FeedPostDto createPost(CreatePostRequest request) {
        UUID agentId = parseUUID(request.agentId());
        if (!agentProfileRepository.existsById(agentId)) {
            throw new ResourceNotFoundException("Agent not found with id: " + agentIdStr(request.agentId()));
        }
        FeedPost post = FeedPost.builder()
                .agentId(agentId)
                .text(request.text())
                .rationale(request.rationale())
                .sources(request.sources() != null ? request.sources() : List.of())
                .build();
        return FeedPostDto.fromEntity(feedPostRepository.save(post));
    }

    @Transactional(readOnly = true)
    public List<AgentProfile> getAllAgents() {
        return agentProfileRepository.findAll();
    }

    public AgentProfile getAgentProfile(String agentIdStr) {
        UUID agentId = parseUUID(agentIdStr);
        return agentProfileRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found with id: " + agentIdStr));
    }

    private UUID parseUUID(String uuidStr) {
        try {
            return UUID.fromString(uuidStr);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Invalid Agent ID format: " + uuidStr);
        }
    }

    private String agentIdStr(String id) {
        return id != null ? id : "null";
    }
}