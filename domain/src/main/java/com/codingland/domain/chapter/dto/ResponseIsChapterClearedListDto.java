package com.codingland.domain.chapter.dto;

import java.util.List;

public record ResponseIsChapterClearedListDto(
        List<ResponseIsChapterClearedDto> result
) {
}
