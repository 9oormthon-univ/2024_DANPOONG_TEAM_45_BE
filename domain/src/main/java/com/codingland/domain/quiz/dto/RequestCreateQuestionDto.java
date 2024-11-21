package com.codingland.domain.quiz.dto;

import com.codingland.domain.quiz.common.BlockTypeEnum;

public record RequestCreateQuestionDto(
        BlockTypeEnum type,
        String msg,
        int repeat
) {
}
