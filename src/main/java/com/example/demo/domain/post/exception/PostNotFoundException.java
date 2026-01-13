package com.example.demo.domain.post.exception;

import com.example.demo.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class PostNotFoundException extends RuntimeException{
      private final ErrorCode errorCode;

    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.POST_NOT_FOUND;
    }

    //    public PostNotFoundException() {
//        super("게시글을 찾을 수 없습니다.");
//    }

}
