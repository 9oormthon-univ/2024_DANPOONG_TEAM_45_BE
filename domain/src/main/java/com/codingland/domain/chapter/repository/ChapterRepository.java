package com.codingland.domain.chapter.repository;

import com.codingland.domain.chapter.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    @Query("""
            SELECT DISTINCT c FROM Chapter c
            LEFT JOIN FETCH c.quizzes
            """)
    List<Chapter> findAllWithQuizzes();
}
