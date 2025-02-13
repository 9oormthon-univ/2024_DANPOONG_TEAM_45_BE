package com.codingland.domain.dailyQuestProgress.entity;

import com.codingland.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyQuestProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    private LocalDate date;

    private int solvedCount;

    private boolean isCompleted;

    @Builder
    public DailyQuestProgress(User user, LocalDate date, int solvedCount, boolean isCompleted) {
        this.user = user;
        this.date = date;
        this.solvedCount = solvedCount;
        this.isCompleted = isCompleted;
    }

    public void updateSolvedCount(int number) {
        this.solvedCount = number;
    }

    public void updateIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
