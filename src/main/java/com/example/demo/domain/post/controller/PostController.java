package com.example.demo.domain.post.controller;

import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PutMapping("/posts/{id}")
    public void update(
            @PathVariable UUID id,
            @RequestParam String title,
            @RequestParam String content
    ) {
        postService.update(id, title, content);
    }

    @DeleteMapping("/posts/{id}")
    public void delete(@PathVariable UUID id){
        postService.delete(id);
    }
}

