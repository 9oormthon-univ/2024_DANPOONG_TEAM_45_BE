package com.codingland.domain.quiz.dto;

public record RequestEditQuizDto(
        Long quizId,
        String title,
        Long chapterId,
        String message,
        int level,
        String hint
) {
}
