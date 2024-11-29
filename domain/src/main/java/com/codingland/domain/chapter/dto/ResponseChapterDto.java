package com.codingland.domain.chapter.dto;

import com.codingland.domain.quiz.dto.ResponseFindQuizByChapter;
import lombok.Builder;

import java.util.List;

@Builder
public record ResponseChapterDto(
        Long id,
        String name,
        boolean isCleared,
        boolean isRewardButtonActive,
        List<ResponseFindQuizByChapter> quizzes
) {
}
