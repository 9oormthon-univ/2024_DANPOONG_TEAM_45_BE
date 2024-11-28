package com.codingland.domain.chapter.service;

import com.codingland.common.exception.chapter.ChapterErrorCode;
import com.codingland.common.exception.chapter.ChapterException;
import com.codingland.common.exception.user.UserErrorCode;
import com.codingland.common.exception.user.UserException;
import com.codingland.domain.chapter.entity.Chapter;
import com.codingland.domain.chapter.entity.HasReceivedReward;
import com.codingland.domain.chapter.repository.ChapterRepository;
import com.codingland.domain.chapter.repository.HasReceivedRewardRepository;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HasReceivedRewardService {
    private final HasReceivedRewardRepository hasReceivedRewardRepository;
    private final ChapterRepository chapterRepository;
    private final UserRepository userRepository;

    /**
     * 챕터 보상 수령 처리 메서드입니다.
     * @author 김원정
     * @param chapter_id 챕터의 id
     * @param user_id 유저의 id
     * @throws ChapterException 챕터가 존재하지 않을 경우 생기는 예외
     */
    @Transactional
    public void processChapterReward(Long chapter_id, Long user_id) {
        Chapter foundChapter = chapterRepository.findById(chapter_id)
                .orElseThrow(() -> new ChapterException(ChapterErrorCode.NOT_FOUND_CHAPTER_ERROR));
        User foundUser = userRepository.findById(user_id)
                .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
        HasReceivedReward hasReceivedReward = HasReceivedReward.thisRewardHasReceived(foundChapter, foundUser);
        hasReceivedRewardRepository.save(hasReceivedReward);
    }
}
