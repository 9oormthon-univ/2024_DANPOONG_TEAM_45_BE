package com.codingland.domain.character.dto;

import com.codingland.domain.character.common.ProgressEnum;
import lombok.Builder;

@Builder
public record ResponseCharacterDetailDto(
        Long id,
        String name,
        int level,
        ProgressEnum type,
        String cactusName,
        String cactusRank,
        int activityPoints
) {
}
