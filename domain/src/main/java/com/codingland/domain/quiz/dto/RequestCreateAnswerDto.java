package com.codingland.domain.quiz.dto;

import com.codingland.domain.quiz.common.BlockTypeEnum;

public record RequestCreateAnswerDto(
        BlockTypeEnum type,
        String msg,
        int repeat
) {
}
