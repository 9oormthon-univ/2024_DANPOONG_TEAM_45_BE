package com.codingland.common.exception.quiz;

import com.codingland.common.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class AnswerException extends RuntimeException {
    private final BaseErrorCode errorCode;
    private final Throwable cause;

    public AnswerException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }
}
