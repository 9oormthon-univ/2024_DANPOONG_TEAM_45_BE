package com.codingland.domain.quiz.repository;

import com.codingland.domain.quiz.entity.IsQuizCleared;
import com.codingland.domain.quiz.entity.Quiz;
import com.codingland.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IsQuizClearedRepository extends JpaRepository<IsQuizCleared, Long> {
    Optional<IsQuizCleared> findByQuizAndUser(Quiz quiz, User user);
}
