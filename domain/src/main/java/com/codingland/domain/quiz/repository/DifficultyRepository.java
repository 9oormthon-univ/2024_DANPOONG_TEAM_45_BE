package com.codingland.domain.quiz.repository;

import com.codingland.domain.quiz.entity.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DifficultyRepository extends JpaRepository<Difficulty, Long> {
    Optional<Difficulty> findByLevel(int level);
}
