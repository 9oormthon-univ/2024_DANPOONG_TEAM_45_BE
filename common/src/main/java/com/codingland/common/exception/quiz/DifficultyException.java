package com.codingland.common.exception.quiz;

import com.codingland.common.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class DifficultyException extends RuntimeException {
    private final BaseErrorCode errorCode;
    private final Throwable cause;

    public DifficultyException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }
}
