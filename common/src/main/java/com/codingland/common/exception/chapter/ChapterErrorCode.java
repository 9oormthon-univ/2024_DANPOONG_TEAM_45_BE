package com.codingland.common.exception.chapter;

import com.codingland.common.common.ApplicationResponse;
import com.codingland.common.common.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChapterErrorCode implements BaseErrorCode {
    NOT_FOUND_CHAPTER_ERROR(HttpStatus.BAD_REQUEST, "2000", "챕터 정보가 존재하지 않습니다.")
    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ApplicationResponse<String> getErrorResponse() {
        return ApplicationResponse.server(code, message);
    }
}
