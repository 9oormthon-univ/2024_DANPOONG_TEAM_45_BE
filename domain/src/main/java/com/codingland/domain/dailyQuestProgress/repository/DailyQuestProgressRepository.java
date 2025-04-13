package com.codingland.domain.dailyQuestProgress.repository;

import com.codingland.domain.dailyQuestProgress.entity.DailyQuestProgress;
import com.codingland.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyQuestProgressRepository extends JpaRepository<DailyQuestProgress, Long> {
    Optional<DailyQuestProgress> findByUserAndDate(User foundUser, LocalDate date);
    List<DailyQuestProgress> findByUser(User user);
}
