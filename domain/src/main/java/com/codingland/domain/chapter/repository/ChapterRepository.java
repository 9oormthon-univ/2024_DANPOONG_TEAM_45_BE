package com.codingland.domain.chapter.repository;

import com.codingland.domain.chapter.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}
