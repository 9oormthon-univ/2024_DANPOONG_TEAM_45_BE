package com.codingland.domain.quiz.repository;

import com.codingland.domain.quiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
