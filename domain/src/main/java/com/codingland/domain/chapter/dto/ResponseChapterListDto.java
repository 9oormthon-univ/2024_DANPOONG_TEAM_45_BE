package com.codingland.domain.chapter.dto;

import java.util.List;

public record ResponseChapterListDto(
        List<ResponseChapterDto> result
) {
}
