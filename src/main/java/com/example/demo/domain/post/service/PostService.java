package com.example.demo.domain.post.service;

import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void create(String title, String content) {
        Post post = new Post(title, content);
        postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional
    public void update(UUID id, String title, String content) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        post.update(title, content);
    }

    public void delete(UUID id) {
        postRepository.deleteById(id);
    }

    // 게시글 단건 조회
    public Post findById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));
    }
}
