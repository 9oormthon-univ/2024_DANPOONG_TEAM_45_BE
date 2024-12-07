package com.codingland.domain.home.dto;

import java.util.List;

public record ResponseHomeListDto(
        List<ResponseHomeForRankingDto> result

) {
}
