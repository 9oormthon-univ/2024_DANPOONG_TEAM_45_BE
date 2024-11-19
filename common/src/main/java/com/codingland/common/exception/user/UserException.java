package com.codingland.common.exception.user;

import com.codingland.common.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private final BaseErrorCode errorCode;

    private final Throwable cause;

    public UserException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }

    public UserException(BaseErrorCode errorCode, Throwable cause) {
        this.errorCode = errorCode;
        this.cause = cause;
    }
}