package com.example.demo.global.error;

public enum ErrorCode {
    POST_NOT_FOUND("POST_NOT_FOUND", "게시글을 찾을 수 없습니다."),
    // [수정] Validation 에러 코드 추가
    VALIDATION_ERROR("VALIDATION_ERROR", "입력값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
