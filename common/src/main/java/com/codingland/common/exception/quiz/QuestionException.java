package com.codingland.common.exception.quiz;

import com.codingland.common.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class QuestionException extends RuntimeException {
    private final BaseErrorCode errorCode;
    private final Throwable cause;

    public QuestionException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }
}
