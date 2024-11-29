package com.codingland.domain.chapter.repository;

import com.codingland.domain.chapter.entity.Chapter;
import com.codingland.domain.chapter.entity.IsChapterCleared;
import com.codingland.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IsChapterClearedRepository extends JpaRepository<IsChapterCleared, Long> {
    List<IsChapterCleared> findAllByUserAndChapterIn(User user, List<Chapter> chapters);
    List<IsChapterCleared> findByUser(User user);
    Optional<IsChapterCleared> findByChapterAndUser(Chapter chapter, User user);
}
