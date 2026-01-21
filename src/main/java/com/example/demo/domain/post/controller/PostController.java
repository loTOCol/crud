package com.example.demo.domain.post.controller;

//import com.example.demo.domain.post.dto.request.PostCreateRequest;
//import com.example.demo.domain.post.dto.request.PostUpdateRequest;
import com.example.demo.domain.post.dto.response.PostResponse;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor // final 필드(PostService)를 생성자로 자동 주입
public class PostController {

    private final PostService postService;

    // 전체 게시글 조회
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.findAll();
        return ResponseEntity.ok(posts);
    }

    // 단일 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable String id){
        Post post = postService.findById(id);
        return ResponseEntity.ok(post);
    }

    // 게시글 생성
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post postRequest){
        Post created = postService.createPost(postRequest.getTitle(),postRequest.getContent());
        return ResponseEntity.ok(created);
    }





}

