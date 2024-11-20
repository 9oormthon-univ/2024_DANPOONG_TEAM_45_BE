package com.codingland.domain.quiz.dto;

public record ResponseIsQuizClearedDto(
        Long id,
        Long userId,
        Long quizId,
        boolean isCleared
) {
}
