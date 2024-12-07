package com.codingland.domain.user.service;


import com.codingland.common.exception.user.UserErrorCode;
import com.codingland.common.exception.user.UserException;
import com.codingland.domain.chapter.entity.HasReceivedReward;
import com.codingland.domain.chapter.entity.IsChapterCleared;
import com.codingland.domain.chapter.repository.HasReceivedRewardRepository;
import com.codingland.domain.chapter.repository.IsChapterClearedRepository;
import com.codingland.domain.character.entity.Character;
import com.codingland.domain.character.repository.CharacterRepository;
import com.codingland.domain.home.repository.HomeRepository;
import com.codingland.domain.quiz.entity.IsQuizCleared;
import com.codingland.domain.quiz.repository.IsQuizClearedRepository;
import com.codingland.domain.user.dto.request.EditUserRequest;
import com.codingland.domain.user.dto.response.FindAllUserResponse;
import com.codingland.domain.user.dto.response.UserResponse;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final IsQuizClearedRepository isQuizClearedRepository;
    private final IsChapterClearedRepository isChapterClearedRepository;
    private final HomeRepository homeRepository;
    private final CharacterRepository characterRepository;
    private final HasReceivedRewardRepository hasReceivedRewardRepository;

    /**
     * 유저 단 건 조회
     * @author 김원정
     * @param user_id 유저의 id
     * @throws UserException 유저를 찾지 못할 경우에 발생하는 예외
     */
    @Transactional(readOnly = true)
    public UserResponse findByUserId(Long user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
        return UserResponse.builder()
                .userId(user.getUserId())
                .subId(user.getSubId())
                .name(user.getName())
                .picture(user.getPicture())
                .email(user.getEmail())
                .build();
    }

    /**
     * 등록된 유저를 모두 조회하는 메서드.
     * @author 김원정
     */
    @Transactional(readOnly = true)
   public FindAllUserResponse findAllUser() {
       List<User> users = userRepository.findAll();
       List<UserResponse> findAllUserResponseList = new ArrayList<>();
       for (User user : users) {
           findAllUserResponseList.add(
                   UserResponse.builder()
                           .userId(user.getUserId())
                           .subId(user.getSubId())
                           .name(user.getName())
                           .picture(user.getPicture())
                           .email(user.getEmail())
                           .build()
           );
       }
       return new FindAllUserResponse(findAllUserResponseList);
   }

    /**
     * 유저를 수정하는 메서드.
     * @author 김원정
     * @param editUserRequest 유저 수정 객체
     * @throws UserException 유저를 찾지 못할 경우에 발생하는 예외
     */
    @Transactional
    public void editUser(EditUserRequest editUserRequest) {
        User foundUser = userRepository.findById(editUserRequest.userId())
                .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
        foundUser.editUser(editUserRequest);
        userRepository.save(foundUser);
    }

    /**
     * 기초 트레이닝 완료 메서드
     * @author 김원정
     * @param user_id 유저의 id
     * @throws UserException 유저를 찾지 못할 경우에 발생하는 예외
     */
    @Transactional
    public void completeTraining(Long user_id) {
        User foundUser = userRepository.findById(user_id)
                .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
        foundUser.completeBasicTraining();
        userRepository.save(foundUser);
    }

    /**
     * 기초 트레이닝 완료 여부 조회
     * @author 김원정
     * @param user_id 유저의 id
     * @throws UserException 유저를 찾지 못할 경우에 발생하는 예외
     */
    @Transactional(readOnly = true)
    public boolean checkIsCompleteTraining(Long user_id) {
        User foundUser = userRepository.findById(user_id)
                .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
        return foundUser.isTrainingComplete();
    }

    /**
     * 회원 탈퇴 메서드입니다.
     * @author 김원정
     * @param user_id 유저의 id
     * @throws UserException 유저를 찾기 못할 경우에 발생하는 예외
     */
    @Transactional
    public void deleteAccount(Long user_id) {

        User foundUser = userRepository.findById(user_id)
                .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
        // 퀴즈 확인 완료 여부 삭제
        List<IsQuizCleared> foundIsQuizClearedList = isQuizClearedRepository.findByUser(foundUser);
        if (!foundIsQuizClearedList.isEmpty()) {
            isQuizClearedRepository.deleteAll(foundIsQuizClearedList);
        }
        // 챕터 확인 완료 여부 삭제
        List<IsChapterCleared> foundIsChapterClearedList = isChapterClearedRepository.findByUser(foundUser);
        if (!foundIsChapterClearedList.isEmpty()) {
            isChapterClearedRepository.deleteAll(foundIsChapterClearedList);
        }
        // 챕터 보상 수령 완료 여부 삭제
        List<HasReceivedReward> foundHasReceivedRewardList = hasReceivedRewardRepository.findByUser(foundUser);
        if (!foundHasReceivedRewardList.isEmpty()) {
            hasReceivedRewardRepository.deleteAll(foundHasReceivedRewardList);
        }
        // 홈 삭제
        homeRepository.findByUser(foundUser).ifPresent(homeRepository::delete);
        // 캐릭터 삭제
        List<Character> foundCharacterList = characterRepository.findCharacterByUser(foundUser);
        if (!foundCharacterList.isEmpty()) {
            characterRepository.deleteAll(foundCharacterList);
        }

        userRepository.delete(foundUser);
    }

}
