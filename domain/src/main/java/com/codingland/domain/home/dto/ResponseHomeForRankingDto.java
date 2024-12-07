package com.codingland.domain.home.dto;

import com.codingland.domain.character.dto.ResponseCharacterDetailDto;

public record ResponseHomeForRankingDto(
        Long id,
        ResponseCharacterDetailDto character,
        String userPicture
) {
}
