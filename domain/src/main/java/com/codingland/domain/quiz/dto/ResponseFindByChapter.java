package com.codingland.domain.quiz.dto;

import lombok.Builder;

@Builder
public record ResponseFindByChapter(
        Long quizId,
        String title,
        int level,
        boolean isCleared
) {
}
