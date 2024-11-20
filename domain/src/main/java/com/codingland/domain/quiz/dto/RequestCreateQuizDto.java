package com.codingland.domain.quiz.dto;

import com.codingland.domain.quiz.common.QuizTypeEnum;

public record RequestCreateQuizDto(
        String question,
        String answer,
        QuizTypeEnum type,
        String title,
        Long chapterId,
        int level
) {
}
