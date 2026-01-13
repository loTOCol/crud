package com.example.demo.global.response;

public record ApiResponse<T>(
        boolean success,
        T data,
        ErrorResponse error
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    // [수정] Record의 success 컴포넌트와 충돌 방지를 위해 메서드명 변경
    // 성공 응답 (body가 없는 경우)
    public static ApiResponse<Void> ok() {
        return new ApiResponse<>(true, null, null);
    }

    public static ApiResponse<?> error(ErrorResponse error) {
        return new ApiResponse<>(false, null, error);
    }
}
