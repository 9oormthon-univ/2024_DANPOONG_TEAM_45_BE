package com.codingland.common.exception.home;

import com.codingland.common.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class HomeException extends RuntimeException {
    private final BaseErrorCode errorCode;
    private final Throwable cause;

    public HomeException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }
}
