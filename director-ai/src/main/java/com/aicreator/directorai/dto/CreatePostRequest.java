package com.aicreator.directorai.dto;

import java.util.List;

public record CreatePostRequest(
        String agentId,
        String text,
        String rationale,
        List<String> sources
) {}