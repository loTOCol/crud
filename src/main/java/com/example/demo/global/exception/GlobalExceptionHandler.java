package com.example.demo.global.exception;

import com.example.demo.domain.post.exception.PostNotFoundException;
import com.example.demo.global.error.ErrorCode;
import com.example.demo.global.response.ApiResponse;
import com.example.demo.global.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handlePostNotFound(PostNotFoundException e) {

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
