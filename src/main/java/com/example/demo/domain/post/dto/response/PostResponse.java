package com.example.demo.domain.post.dto.response;

import com.example.demo.domain.post.entity.Post;

import java.util.UUID;

public record PostResponse(
        UUID id,
        String title,
        String content
) {
    public PostResponse(Post post){
        this(post.getId(),post.getTitle(),post.getContent());
    }
}
