package com.codingland.domain.chapter.dto;

import lombok.Builder;

@Builder
public record ResponseIsChapterClearedDto(
        Long id,
        Long userId,
        Long ChapterId,
        boolean isCleared
) {
}
