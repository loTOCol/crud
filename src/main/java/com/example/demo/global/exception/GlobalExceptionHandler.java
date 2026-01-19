package com.example.demo.global.exception;

import com.example.demo.domain.post.exception.PostNotFoundException;
import com.example.demo.global.error.ErrorCode;
import com.example.demo.global.response.ApiResponse;
import com.example.demo.global.response.ErrorResponse;
// [수정] 로깅을 위한 Slf4j 추가
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// [수정] 로깅을 위한 @Slf4j 추가
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handlePostNotFound(PostNotFoundException e) {
        // [수정] PostNotFoundException 로깅
        log.warn("Post not found. code={}", e.getErrorCode().getCode(), e);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(
                        ErrorResponse.from(e.getErrorCode())
                ));
    }

    // [수정] Validation 예외 처리 추가
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        String errorMessage = String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage());

        // [수정] Validation 에러 로깅
        log.warn("Validation error. field={}, message={}", fieldError.getField(), fieldError.getDefaultMessage());
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        new ErrorResponse(
                                ErrorCode.VALIDATION_ERROR.getCode(),
                                errorMessage
                        )
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        // [수정] 알 수 없는 서버 에러 로깅
        log.error("Unexpected server error", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                        new ErrorResponse(
                                "INTERNAL_SERVER_ERROR",
                                "서버 오류가 발생했습니다."
                        )
                ));
    }
}
