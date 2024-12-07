package com.codingland.common.exception.chapter;

import com.codingland.common.common.ApplicationResponse;
import com.codingland.common.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HasReceivedRewardErrorCode implements BaseErrorCode {
    ALREADY_EXIST(HttpStatus.BAD_REQUEST, "2000", "챕터 보상을 이미 받았습니다.")
    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ApplicationResponse<String> getErrorResponse() {
        return ApplicationResponse.server(code, message);
    }
}
