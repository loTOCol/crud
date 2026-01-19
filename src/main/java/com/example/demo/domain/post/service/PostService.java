package com.example.demo.domain.post.service;

import com.example.demo.domain.post.dto.request.PostCreateRequest;
import com.example.demo.domain.post.dto.request.PostUpdateRequest;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.exception.PostNotFoundException;
import com.example.demo.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
// [수정] 로깅을 위한 Slf4j 추가
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

// [수정] 로깅을 위한 @Slf4j 추가
@Slf4j
@Service
public class PostService {
    private final PostRepository postRepository;


    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // [수정] 개별 파라미터 대신 DTO를 직접 받도록 변경
    @Transactional
    public void create(PostCreateRequest request) {
        // [수정] 서비스 레이어 생성 로깅
        log.debug("Create post in service. title={}", request.title());
        Post post = new Post(request.title(), request.content());
        postRepository.save(post);
    }

    // [수정] 페이징 처리 추가
    public Page<Post> findAll(Pageable pageable) {
        // [수정] 서비스 레이어 목록 조회 로깅
        log.debug("Find all posts in service. page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return postRepository.findAll(pageable);
    }

    // [수정] 개별 파라미터 대신 DTO를 직접 받도록 변경
    @Transactional
    public void update(UUID id, PostUpdateRequest request) {
        // [수정] 서비스 레이어 수정 로깅
        log.debug("Update post in service. id={}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        post.update(request.title(), request.content());
    }

    @Transactional
    public void delete(UUID id) {
        // [수정] 서비스 레이어 삭제 로깅
        log.debug("Delete post in service. id={}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
    }

    // 게시글 단건 조회
    public Post findById(UUID id) {
        // [수정] 서비스 레이어 단건 조회 로깅
        log.debug("Find post in service. id={}", id);
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }
}
