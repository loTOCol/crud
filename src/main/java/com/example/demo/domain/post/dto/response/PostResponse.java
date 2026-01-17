package com.example.demo.domain.post.dto.response;

import com.example.demo.domain.post.entity.Post;

import java.time.LocalDateTime;
import java.util.UUID;

// [수정] createdAt, updatedAt 필드 추가
public record PostResponse(
        UUID id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public PostResponse(Post post){
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
