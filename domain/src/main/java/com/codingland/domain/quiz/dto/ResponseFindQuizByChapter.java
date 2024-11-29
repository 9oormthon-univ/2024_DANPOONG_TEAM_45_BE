package com.codingland.domain.quiz.dto;

import lombok.Builder;

@Builder
public record ResponseFindQuizByChapter(
        Long quizId,
        String title,
        int level,
        boolean isCleared
) {
}
