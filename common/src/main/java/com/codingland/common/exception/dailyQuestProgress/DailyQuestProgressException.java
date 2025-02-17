package com.codingland.common.exception.dailyQuestProgress;

import com.codingland.common.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class DailyQuestProgressException extends RuntimeException {
    private final BaseErrorCode errorCode;
    private final Throwable cause;

    public DailyQuestProgressException(BaseErrorCode errorCode) {
        this.errorCode = errorCode;
        this.cause = null;
    }
}
