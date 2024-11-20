package com.codingland.domain.quiz.dto;

import java.util.List;

public record ResponseFindByChapterListDto(
        List<ResponseFindByChapter> result
) {
}
