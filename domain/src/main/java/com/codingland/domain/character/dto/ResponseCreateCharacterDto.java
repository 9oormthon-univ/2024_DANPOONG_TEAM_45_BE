package com.codingland.domain.character.dto;

public record ResponseCreateCharacterDto(
        Long characterId,
        Long homeId
) {
}
