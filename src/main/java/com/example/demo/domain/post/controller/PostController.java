package com.example.demo.domain.post.controller;

import com.example.demo.domain.post.dto.request.PostCreateRequest;
import com.example.demo.domain.post.dto.request.PostUpdateRequest;
import com.example.demo.domain.post.dto.response.PostResponse;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.service.PostService;
import jakarta.validation.Valid;
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
    // [수정] @Valid 어노테이션 추가로 validation 활성화
    public ApiResponse<Void> create(@Valid @RequestBody PostCreateRequest request){
        // [수정] DTO를 직접 전달하도록 변경
        postService.create(request);
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
    // [수정] @Valid 어노테이션 추가로 validation 활성화
    public ApiResponse<Void> update(
            @PathVariable UUID id,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        // [수정] DTO를 직접 전달하도록 변경
        postService.update(id, request);
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

