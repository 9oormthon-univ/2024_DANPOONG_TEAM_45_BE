package com.codingland.domain.quiz.dto;

import com.codingland.domain.quiz.common.BlockTypeEnum;
import lombok.Builder;

@Builder
public record ResponseQuestionDto(
    Long id,
    BlockTypeEnum type,
    String msg,
    int repeat
) {
}
