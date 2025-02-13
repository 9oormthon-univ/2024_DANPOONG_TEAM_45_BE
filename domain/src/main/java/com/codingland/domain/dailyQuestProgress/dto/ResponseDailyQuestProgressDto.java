package com.codingland.domain.dailyQuestProgress.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ResponseDailyQuestProgressDto(
    Long id,
    Long userId,
    LocalDate date,
    int solvedCount,
    boolean isCompleted
) {
}
