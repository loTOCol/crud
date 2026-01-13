package com.example.demo.domain.post.controller;

import com.example.demo.domain.post.dto.request.PostCreateRequest;
import com.example.demo.domain.post.dto.request.PostUpdateRequest;
import com.example.demo.domain.post.dto.response.PostResponse;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.service.PostService;
import org.springframework.web.bind.annotation.*;
import com.example.demo.global.response.ApiResponse;

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
    public ApiResponse<Void> create(@RequestBody PostCreateRequest request){
        postService.create(request.title(), request.content());
        // [수정] success() -> ok()로 변경 (Record 컴포넌트와 충돌 방지)
        return ApiResponse.ok();
    }


    // 게시글 조회
    @GetMapping
    public ApiResponse<List<PostResponse>> findAll() {
        return ApiResponse.success(
                postService.findAll()
                        .stream()
                        .map(PostResponse::new)
                        .toList()
        );
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public ApiResponse<PostResponse> findOne(@PathVariable UUID id) {
        return ApiResponse.success(
                new PostResponse(postService.findById(id))
        );
    }


    @PutMapping("/{id}")
    public ApiResponse<Void> update(
            @PathVariable UUID id,
            @RequestBody PostUpdateRequest request
    ) {
        postService.update(id, request.title(), request.content());
        // [수정] success() -> ok()로 변경
        return ApiResponse.ok();
    }


    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id){
        postService.delete(id);
        // [수정] success() -> ok()로 변경
        return ApiResponse.ok();
    }

}

