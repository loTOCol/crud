package com.example.demo.domain.post.controller;

import com.example.demo.domain.post.dto.request.PostCreateRequest;
import com.example.demo.domain.post.dto.request.PostUpdateRequest;
import com.example.demo.domain.post.dto.response.PostResponse;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.service.PostService;
import com.example.demo.global.response.ApiResponse;
import jakarta.validation.Valid;
// [수정] 로깅을 위한 Slf4j 추가
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
// [수정] 상태 코드 지정을 위한 ResponseEntity, HttpStatus 추가
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// [수정] 로깅을 위한 @Slf4j 추가
@Slf4j
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
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody PostCreateRequest request){
        // [수정] 생성 요청 로깅
        log.info("Create post request. title={}", request.title());
        // [수정] DTO를 직접 전달하도록 변경
        postService.create(request);
        // [수정] success() -> ok()로 변경 (Record 컴포넌트와 충돌 방지)
        // [수정] HTTP 201 Created로 응답
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok());
    }


    // 게시글 조회
    @GetMapping
    // [수정] 페이징 처리 추가 (기본값: size=20, sort=createdAt,desc)
    public ApiResponse<Page<PostResponse>> findAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable
    ) {
        // [수정] 조회 요청 로깅
        log.info("Find all posts. page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        // [수정] Page<Post>를 Page<PostResponse>로 변환
        Page<Post> postPage = postService.findAll(pageable);
        Page<PostResponse> responsePage = postPage.map(PostResponse::new);
        return ApiResponse.success(responsePage);
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public ApiResponse<PostResponse> findOne(@PathVariable UUID id) {
        // [수정] 단건 조회 로깅
        log.info("Find post. id={}", id);
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
        // [수정] 수정 요청 로깅
        log.info("Update post. id={}", id);
        // [수정] DTO를 직접 전달하도록 변경
        postService.update(id, request);
        // [수정] success() -> ok()로 변경
        return ApiResponse.ok();
    }


    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id){
        // [수정] 삭제 요청 로깅
        log.info("Delete post. id={}", id);
        postService.delete(id);
        // [수정] success() -> ok()로 변경
        return ApiResponse.ok();
    }

}

