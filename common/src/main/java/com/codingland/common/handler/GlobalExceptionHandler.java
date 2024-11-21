package com.codingland.common.handler;

import com.codingland.common.common.ApplicationResponse;
import com.codingland.common.common.ApplicationResult;
import com.codingland.common.exception.chapter.ChapterException;
import com.codingland.common.exception.chapter.IsChapterClearedException;
import com.codingland.common.exception.character.CharacterException;
import com.codingland.common.exception.home.HomeException;
import com.codingland.common.exception.quiz.DifficultyException;
import com.codingland.common.exception.quiz.IsQuizClearedException;
import com.codingland.common.exception.quiz.QuizException;
import com.codingland.common.exception.security.SecurityCustomException;
import com.codingland.common.exception.user.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SecurityCustomException.class)
    public ResponseEntity<ApplicationResponse<String>> handleSecurityException(SecurityCustomException ex) {
        ApplicationResponse<String> response = new ApplicationResponse<>(
                new ApplicationResult(Integer.parseInt(ex.getErrorCode().getCode()), ex.getErrorCode().getMessage()),
                null
        );
        return new ResponseEntity<>(response, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(ChapterException.class)
    public ResponseEntity<ApplicationResponse<String>> handleChapterException(ChapterException ex) {
        ApplicationResponse<String> response = new ApplicationResponse<>(
                new ApplicationResult(Integer.parseInt(ex.getErrorCode().getCode()), ex.getErrorCode().getMessage()),
                null
        );
        return new ResponseEntity<>(response, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(CharacterException.class)
    public ResponseEntity<ApplicationResponse<String>> handleCharacterException(CharacterException ex) {
        ApplicationResponse<String> response = new ApplicationResponse<>(
                new ApplicationResult(Integer.parseInt(ex.getErrorCode().getCode()), ex.getErrorCode().getMessage()),
                null
        );
        return new ResponseEntity<>(response, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(HomeException.class)
    public ResponseEntity<ApplicationResponse<String>> handleHomeException(HomeException ex) {
        ApplicationResponse<String> response = new ApplicationResponse<>(
                new ApplicationResult(Integer.parseInt(ex.getErrorCode().getCode()), ex.getErrorCode().getMessage()),
                null
        );
        return new ResponseEntity<>(response, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(DifficultyException.class)
    public ResponseEntity<ApplicationResponse<String>> handleDifficultyException(DifficultyException ex) {
        ApplicationResponse<String> response = new ApplicationResponse<>(
                new ApplicationResult(Integer.parseInt(ex.getErrorCode().getCode()), ex.getErrorCode().getMessage()),
                null
        );
        return new ResponseEntity<>(response, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(QuizException.class)
    public ResponseEntity<ApplicationResponse<String>> handleQuizException(QuizException ex) {
        ApplicationResponse<String> response = new ApplicationResponse<>(
                new ApplicationResult(Integer.parseInt(ex.getErrorCode().getCode()), ex.getErrorCode().getMessage()),
                null
        );
        return new ResponseEntity<>(response, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApplicationResponse<String>> handleUserException(UserException ex) {
        ApplicationResponse<String> response = new ApplicationResponse<>(
                new ApplicationResult(Integer.parseInt(ex.getErrorCode().getCode()), ex.getErrorCode().getMessage()),
                null
        );
        return new ResponseEntity<>(response, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(IsQuizClearedException.class)
    public ResponseEntity<ApplicationResponse<String>> handleUserException(IsQuizClearedException ex) {
        ApplicationResponse<String> response = new ApplicationResponse<>(
                new ApplicationResult(Integer.parseInt(ex.getErrorCode().getCode()), ex.getErrorCode().getMessage()),
                null
        );
        return new ResponseEntity<>(response, ex.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(IsChapterClearedException.class)
    public ResponseEntity<ApplicationResponse<String>> handleUserException(IsChapterClearedException ex) {
        ApplicationResponse<String> response = new ApplicationResponse<>(
                new ApplicationResult(Integer.parseInt(ex.getErrorCode().getCode()), ex.getErrorCode().getMessage()),
                null
        );
        return new ResponseEntity<>(response, ex.getErrorCode().getHttpStatus());
    }
}
