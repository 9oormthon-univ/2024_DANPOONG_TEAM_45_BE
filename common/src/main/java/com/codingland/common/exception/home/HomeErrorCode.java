package com.codingland.common.exception.home;

import com.codingland.common.common.ApplicationResponse;
import com.codingland.common.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HomeErrorCode implements BaseErrorCode {
    NO_HOME_INFO(HttpStatus.NOT_FOUND, "3000", "홈 정보가 존재하지 않습니다."),
    HOME_CREATION_FAILED(HttpStatus.BAD_REQUEST, "3001", "홈 생성에 실패했습니다."),
    HOME_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "3002", "홈 업데이트에 실패했습니다."),
    HOME_DELETION_FAILED(HttpStatus.BAD_REQUEST, "3003", "홈 삭제에 실패했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ApplicationResponse<String> getErrorResponse() {
        return ApplicationResponse.server(code, message);
    }
}
