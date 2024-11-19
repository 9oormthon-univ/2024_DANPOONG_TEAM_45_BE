package com.codingland.domain.home.dto;

public record RequestEditHomeDto(
        //{id, character_id, character_type, character_name}
        Long id,
        Long characterId,
        String characterType,
        String characterName
) {
}
