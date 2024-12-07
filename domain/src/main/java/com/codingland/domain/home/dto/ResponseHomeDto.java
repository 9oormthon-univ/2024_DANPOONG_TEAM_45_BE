package com.codingland.domain.home.dto;

import com.codingland.domain.character.dto.ResponseCharacterDto;

public record ResponseHomeDto(
        //{id, character_id, character_type, character_name}
        Long id,
        ResponseCharacterDto character,
        String userPicture
){
}
