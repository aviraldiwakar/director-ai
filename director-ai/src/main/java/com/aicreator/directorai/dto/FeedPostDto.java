package com.aicreator.directorai.dto;

import com.aicreator.directorai.entity.FeedPost;

import java.time.format.DateTimeFormatter;
import java.util.List;

public record FeedPostDto(
        String id,
        String createdAt,
        String text,
        String rationale,
        List<String> sources
) {
    public static FeedPostDto fromEntity(FeedPost post) {
        return new FeedPostDto(
                post.getId().toString(),
                DateTimeFormatter.ISO_INSTANT.format(post.getCreatedAt()),
                post.getText(),
                post.getRationale(),
                post.getSources()
        );
    }
}