package com.example.demo.domain.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MYSQL strategy IDENTITY 명시 권장
    private Long id;

    private String title;
    private String content;

    protected Post(){
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
