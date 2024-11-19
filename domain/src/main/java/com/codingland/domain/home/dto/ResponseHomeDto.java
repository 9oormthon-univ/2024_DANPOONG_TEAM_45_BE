package com.codingland.domain.home.dto;

import com.codingland.domain.home.common.HomeTypeEnum;

public record ResponseHomeDto(
        //{id, character_id, character_type, character_name}
        Long id,
        Long characterId,
        String characterType,
        String characterName


){
}
