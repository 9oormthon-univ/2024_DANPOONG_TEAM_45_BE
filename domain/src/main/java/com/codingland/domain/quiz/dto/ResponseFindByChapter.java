package com.codingland.domain.quiz.dto;

import com.codingland.domain.quiz.common.QuizTypeEnum;
import lombok.Builder;

@Builder
public record ResponseFindByChapter(
        Long quizId,
        QuizTypeEnum type,
        String title,
        int level,
        boolean isCleared
) {
}
