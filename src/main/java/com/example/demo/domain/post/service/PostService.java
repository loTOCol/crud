package com.example.demo.domain.post.service;

import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.exception.PostNotFoundException;
import com.example.demo.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
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
                .orElseThrow(PostNotFoundException::new);

        post.update(title, content);
    }

    @Transactional
    public void delete(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
    }

    // 게시글 단건 조회
    public Post findById(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }
}
