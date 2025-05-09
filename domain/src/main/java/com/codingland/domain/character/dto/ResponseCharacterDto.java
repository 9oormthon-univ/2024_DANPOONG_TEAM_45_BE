package com.codingland.domain.character.dto;

import com.codingland.domain.character.common.CactusType;
import com.codingland.domain.character.common.ProgressEnum;
import lombok.Builder;

@Builder
public record ResponseCharacterDto(
        Long id,
        String name,
        int level,
        ProgressEnum type,
        CactusType cactusType,
        int activityPoints
) {
}
