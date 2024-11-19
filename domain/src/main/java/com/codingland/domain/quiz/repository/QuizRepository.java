package com.codingland.domain.quiz.repository;

import com.codingland.domain.quiz.entity.Difficulty;
import com.codingland.domain.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findAllByDifficulty(Difficulty difficulty);
}
