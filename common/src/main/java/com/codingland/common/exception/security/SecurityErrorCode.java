package com.codingland.common.exception.security;

import com.codingland.common.common.ApplicationResponse;
import com.codingland.common.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SecurityErrorCode implements BaseErrorCode {

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "SEC4001", "잘못된 형식의 토큰입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "SEC4010", "인증이 필요합니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "SEC4011", "토큰이 만료되었습니다."),
    TOKEN_SIGNATURE_ERROR(HttpStatus.UNAUTHORIZED, "SEC4012", "토큰이 위조되었거나 손상되었습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "SEC4030", "권한이 없습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "SEC4041", "토큰이 존재하지 않습니다."),
    TOKEN_ORGANIZATION_NOT_FOND(HttpStatus.UNAUTHORIZED, "SEC4042", "존재하지 않는 단체입니다."),
    INTERNAL_SECURITY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SEC5000", "인증 처리 중 서버 에러가 발생했습니다."),
    INTERNAL_TOKEN_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SEC5001", "토큰 처리 중 서버 에러가 발생했습니다."),

    KAKAO_KEY_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "U004", "카카오 공개 키 서버와 통신 중 오류가 발생했습니다. 다시 시도해 주세요."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ApplicationResponse<String> getErrorResponse() {
        return ApplicationResponse.server(code, message);
    }
}
