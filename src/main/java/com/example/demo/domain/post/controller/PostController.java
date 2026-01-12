package com.example.demo.domain.post.controller;

import com.example.demo.domain.post.dto.request.PostCreateRequest;
import com.example.demo.domain.post.dto.request.PostUpdateRequest;
import com.example.demo.domain.post.dto.respone.PostResponse;
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

    // 게시글 생성
    @PostMapping
    public void create(@RequestBody PostCreateRequest request){
        postService.create(request.title(), request.content());
    }

    // 게시글 조회
    @GetMapping
    public List<PostResponse> findAll() {
        return postService.findAll()
                .stream()
                .map(PostResponse::new)
                .toList();
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public PostResponse findOne(@PathVariable UUID id) {
        return new PostResponse(postService.findById(id));
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable UUID id,
            @RequestBody PostUpdateRequest request
    ) {
        postService.update(id, request.title(), request.content());
    }

    @DeleteMapping("/posts/{id}")
    public void delete(@PathVariable UUID id){
        postService.delete(id);
    }



}

