package com.codingland.common.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class ApplicationResponse<T> {
    private ApplicationResult result;
    private T payload;

    public static <T> ApplicationResponse<T> ok(T payload) {
        return ApplicationResponse.<T>builder()
                .result(ApplicationResult.builder()
                        .code(HttpStatus.OK.value())
                        .message("API 호출 성공")
                        .build())
                .payload(payload)
                .build();
    }

    public static <T> ApplicationResponse<T> ok(T payload, String message) {
        return ApplicationResponse.<T>builder()
                .result(ApplicationResult.builder()
                        .code(HttpStatus.OK.value())
                        .message(message)
                        .build())
                .payload(payload)
                .build();
    }

    public static <T> ApplicationResponse<T> badRequest(T payload) {
        return ApplicationResponse.<T>builder()
                .result(ApplicationResult.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("잘못된 요청입니다.")
                        .build())
                .payload(payload)
                .build();
    }

    public static <T> ApplicationResponse<T> badRequest(T payload, String message) {
        return ApplicationResponse.<T>builder()
                .result(ApplicationResult.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .build())
                .payload(payload)
                .build();
    }

    public static <T> ApplicationResponse<T> notAuthenticated(T payload) {
        return ApplicationResponse.<T>builder()
                .result(ApplicationResult.builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .message("잘못된 접근입니다.")
                        .build())
                .payload(payload)
                .build();
    }

    public static <T> ApplicationResponse<T> notAuthenticated(T payload, String message) {
        return ApplicationResponse.<T>builder()
                .result(ApplicationResult.builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .message(message)
                        .build())
                .payload(payload)
                .build();
    }

    public static <T> ApplicationResponse<T> server(T payload) {
        return ApplicationResponse.<T>builder()
                .result(ApplicationResult.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("API 호출 실패")
                        .build())
                .payload(payload)
                .build();
    }

    public static <T> ApplicationResponse<T> server(T payload, String message) {
        return ApplicationResponse.<T>builder()
                .result(ApplicationResult.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(message)
                        .build())
                .payload(payload)
                .build();
    }

    public static <T> ApplicationResponse<T> custom(T payload, Integer code, String message) {
        return ApplicationResponse.<T>builder()
                .result(ApplicationResult.builder()
                        .code(code)
                        .message(message)
                        .build())
                .payload(payload)
                .build();
    }
}
