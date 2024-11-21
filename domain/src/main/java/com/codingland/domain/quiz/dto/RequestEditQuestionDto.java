package com.codingland.domain.quiz.dto;

import com.codingland.domain.quiz.common.BlockTypeEnum;

public record RequestEditQuestionDto(
        Long answerId,
        BlockTypeEnum type,
        String msg,
        int repeat
) {
}
