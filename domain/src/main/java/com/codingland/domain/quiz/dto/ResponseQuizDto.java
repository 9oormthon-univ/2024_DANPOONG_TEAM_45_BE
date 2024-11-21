package com.codingland.domain.quiz.dto;

import com.codingland.domain.quiz.common.BlockTypeEnum;
import lombok.Builder;

import java.util.List;

@Builder
public record ResponseQuizDto(
        Long quizId,
        List<ResponseQuestionDto> questions,
        List<ResponseAnswerDto> answers,
        BlockTypeEnum type,
        String title,
        String message,
        Long chapterId,
        int level,
        boolean isCleared,
        String hint
){
}
