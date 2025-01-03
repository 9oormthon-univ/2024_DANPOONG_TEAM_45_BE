package com.codingland.common.exception.quiz;

import com.codingland.common.common.ApplicationResponse;
import com.codingland.common.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum IsQuizClearedErrorCode implements BaseErrorCode {
    NOT_FOUND_QUIZ_ERROR(HttpStatus.BAD_REQUEST, "2000", "퀴즈 완료 정보가 존재하지 않습니다."),
    ALREADY_EXIST(HttpStatus.BAD_REQUEST, "2000", "퀴즈 완료 정보가 이미 존재합니다.")
    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ApplicationResponse<String> getErrorResponse() {
        return ApplicationResponse.server(code, message);
    }
}
