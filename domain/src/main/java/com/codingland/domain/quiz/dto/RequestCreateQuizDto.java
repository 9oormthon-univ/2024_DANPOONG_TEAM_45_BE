package com.codingland.domain.quiz.dto;


import java.util.List;

public record RequestCreateQuizDto(
        List<RequestCreateQuestionDto> questions,
        List<RequestCreateAnswerDto> answers,
        String title,
        String message,
        Long chapterId,
        int level,
        String hint
) {
}
