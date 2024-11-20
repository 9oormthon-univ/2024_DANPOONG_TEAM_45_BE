package com.codingland.domain.quiz.dto;

import java.util.List;

public record ResponseIsQuizClearedListDto(
        List<ResponseIsQuizClearedDto> result
) {
}
