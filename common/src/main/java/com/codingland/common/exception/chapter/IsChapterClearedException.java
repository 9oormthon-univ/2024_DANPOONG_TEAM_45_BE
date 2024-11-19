package com.codingland.common.exception.chapter;

import com.codingland.common.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class IsChapterClearedException extends RuntimeException {
    private final BaseErrorCode errorCode;
    private final Throwable cause;

    public IsChapterClearedException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }
}
