package com.codingland.common.common;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    HttpStatus getHttpStatus();

    String getCode();

    String getMessage();

    ApplicationResponse<String> getErrorResponse();
}
