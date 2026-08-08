package com.aicreator.directorai.dto;

import java.util.List;

public record FeedResponse(
        List<FeedPostDto> posts
) {}