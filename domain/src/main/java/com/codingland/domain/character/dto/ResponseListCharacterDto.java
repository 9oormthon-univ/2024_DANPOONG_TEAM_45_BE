package com.codingland.domain.character.dto;

import java.util.List;

public record ResponseListCharacterDto(
        List<ResponseCharacterDetailDto> result
) {
}
