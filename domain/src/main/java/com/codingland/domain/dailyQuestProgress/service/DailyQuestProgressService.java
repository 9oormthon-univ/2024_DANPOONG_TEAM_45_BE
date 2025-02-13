package com.codingland.domain.dailyQuestProgress.service;

import com.codingland.common.exception.user.UserErrorCode;
import com.codingland.common.exception.user.UserException;
import com.codingland.domain.dailyQuestProgress.dto.ResponseDailyQuestProgressDto;
import com.codingland.domain.dailyQuestProgress.entity.DailyQuestProgress;
import com.codingland.domain.dailyQuestProgress.repository.DailyQuestProgressRepository;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyQuestProgressService {
    private final UserRepository userRepository;
    private final DailyQuestProgressRepository dailyQuestProgressRepository;

    /**
     * 사용자의 DailyQuestProgress의 정보를 반환하는 API
     * DailyQuestProgress가 존재하지 않는다면 새로운 Entity를 생성함
     * 새로운 Entity라면 id가 존재하지 않기 때문에 이를 통해 구분하여 저장함
     * Entity가 이미 존재한다면
     * @author 김원정
     */
    @Transactional
    public ResponseDailyQuestProgressDto getDailyQuestProgress(Long user_id) {
        LocalDate today = LocalDate.now();
        User foundUser = userRepository.findById(user_id)
                .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
        DailyQuestProgress foundDailyQuestProgress = dailyQuestProgressRepository.findByUserAndDate(foundUser, today)
                .orElseGet(() ->
                        DailyQuestProgress.builder()
                                .user(foundUser)
                                .date(today)
                                .solvedCount(0)
                                .isCompleted(false)
                                .build());

        if (foundDailyQuestProgress.getId() == null) {
            foundDailyQuestProgress = dailyQuestProgressRepository.save(foundDailyQuestProgress);
        }
        return ResponseDailyQuestProgressDto.builder()
                .id(foundDailyQuestProgress.getId())
                .date(foundDailyQuestProgress.getDate())
                .solvedCount(foundDailyQuestProgress.getSolvedCount())
                .isCompleted(foundDailyQuestProgress.isCompleted())
                .userId(foundDailyQuestProgress.getUser().getUserId())
                .build();
    }
}
