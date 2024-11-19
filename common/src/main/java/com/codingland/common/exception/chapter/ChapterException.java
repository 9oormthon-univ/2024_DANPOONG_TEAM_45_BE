package com.codingland.common.exception.chapter;

import com.codingland.common.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class ChapterException extends RuntimeException {
    private final BaseErrorCode errorCode;
    private final Throwable cause;

    public ChapterException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }
}
