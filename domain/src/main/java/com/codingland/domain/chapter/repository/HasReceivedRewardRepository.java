package com.codingland.domain.chapter.repository;

import com.codingland.domain.chapter.entity.Chapter;
import com.codingland.domain.chapter.entity.HasReceivedReward;
import com.codingland.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.Optional;

public interface HasReceivedRewardRepository extends JpaRepository<HasReceivedReward, Long> {
    Optional<HasReceivedReward> findByChapterAndUser(Chapter chapter, User uesr);
    List<HasReceivedReward> findByUser(User user);
}
