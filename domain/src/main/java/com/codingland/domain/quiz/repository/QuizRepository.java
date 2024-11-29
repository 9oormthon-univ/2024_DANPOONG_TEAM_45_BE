package com.codingland.domain.quiz.repository;

import com.codingland.domain.quiz.entity.Difficulty;
import com.codingland.domain.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query("""
            SELECT q FROM Quiz q
            JOIN FETCH q.chapter
            JOIN FETCH q.difficulty
            """)
    List<Quiz> findAllWithChapterAndDifficulty();
    List<Quiz> findAllByDifficulty(Difficulty difficulty);
}
