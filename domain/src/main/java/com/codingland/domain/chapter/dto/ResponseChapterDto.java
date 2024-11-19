package com.codingland.domain.chapter.dto;

import com.codingland.domain.quiz.dto.ResponseFindByChapter;

import java.util.List;

public record ResponseChapterDto(
        Long id,
        String name,
        boolean isCleared,
        List<ResponseFindByChapter> quizzes
) {
}
