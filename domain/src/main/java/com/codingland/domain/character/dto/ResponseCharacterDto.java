package com.codingland.domain.character.dto;

import com.codingland.domain.character.common.CharacterTypeEnum;
import lombok.Builder;

@Builder
public record ResponseCharacterDto(
        Long id,
        String name,
        int level,
        CharacterTypeEnum type,
        int activityPoints
) {
}
