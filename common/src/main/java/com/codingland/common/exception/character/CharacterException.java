package com.codingland.common.exception.character;

import com.codingland.common.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class CharacterException extends RuntimeException {
    private final BaseErrorCode errorCode;
    private final Throwable cause;

    public CharacterException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }
}
