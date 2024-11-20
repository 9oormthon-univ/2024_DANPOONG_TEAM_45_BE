package com.codingland.domain.quiz.dto;

import java.util.List;

public record ResponseQuizListDto(
        List<ResponseQuizDto> result
) {
}
