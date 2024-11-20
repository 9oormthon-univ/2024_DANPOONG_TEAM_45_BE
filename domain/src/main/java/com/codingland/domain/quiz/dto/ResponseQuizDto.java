package com.codingland.domain.quiz.dto;

import com.codingland.domain.quiz.common.QuizTypeEnum;
import lombok.Builder;

@Builder
public record ResponseQuizDto(
        Long quizId,
        String question,
        String answer,
        QuizTypeEnum type,
        String title,
        Long chapterId,
        int level,
        boolean isCleared
){
}
