package com.aicreator.directorai.controller;

import com.aicreator.directorai.dto.CreatePostRequest;
import com.aicreator.directorai.dto.FeedPostDto;
import com.aicreator.directorai.dto.FeedResponse;
import com.aicreator.directorai.dto.InitAgentRequest;
import com.aicreator.directorai.dto.InitAgentResponse;
import com.aicreator.directorai.entity.AgentProfile;
import com.aicreator.directorai.service.AgentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agent")
@CrossOrigin
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @PostMapping("/init")
    public ResponseEntity<InitAgentResponse> initAgent(@Valid @RequestBody InitAgentRequest request) {
        InitAgentResponse response = agentService.initAgent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedResponse> getFeed(@RequestParam("agentId") String agentId) {
        FeedResponse feed = agentService.getFeed(agentId);
        return ResponseEntity.ok(feed);
    }

    @PostMapping("/posts")
    public ResponseEntity<FeedPostDto> createPost(@RequestBody CreatePostRequest request) {
        FeedPostDto post = agentService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping("/profile")
    public ResponseEntity<AgentProfile> getProfile(@RequestParam("agentId") String agentId) {
        return ResponseEntity.ok(agentService.getAgentProfile(agentId));
    }

    @GetMapping("/all")
    public ResponseEntity<java.util.List<AgentProfile>> getAllAgents() {
        return ResponseEntity.ok(agentService.getAllAgents());
    }
}