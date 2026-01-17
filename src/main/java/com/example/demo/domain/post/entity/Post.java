package com.example.demo.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

// [수정] JPA Auditing 활성화를 위한 EntityListener 추가
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
public class Post {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String title;
    private String content;

    // [수정] 생성일시 필드 추가 (JPA Auditing으로 자동 관리)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // [수정] 수정일시 필드 추가 (JPA Auditing으로 자동 관리)
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected Post(){
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
