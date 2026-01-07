package com.example.demo.domain.post.controller;

import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public void create(@RequestBody Post post) {
        postService.create(post.getTitle(), post.getContent());
    }

    @GetMapping
    public List<Post> findAll() {
        return postService.findAll();
    }
}

