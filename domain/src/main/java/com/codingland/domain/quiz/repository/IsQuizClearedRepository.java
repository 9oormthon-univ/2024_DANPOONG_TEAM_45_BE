package com.codingland.domain.quiz.repository;

import com.codingland.domain.quiz.entity.IsQuizCleared;
import com.codingland.domain.quiz.entity.Quiz;
import com.codingland.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IsQuizClearedRepository extends JpaRepository<IsQuizCleared, Long> {
    List<IsQuizCleared> findAllByUserAndQuizIn(User user, List<Quiz> quizzes);
    List<IsQuizCleared> findByUser(User user);
    Optional<IsQuizCleared> findByQuizAndUser(Quiz quiz, User user);
}
