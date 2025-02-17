package com.codingland.domain.quiz.service;

import com.codingland.common.exception.quiz.IsQuizClearedErrorCode;
import com.codingland.common.exception.quiz.IsQuizClearedException;
import com.codingland.common.exception.quiz.QuizErrorCode;
import com.codingland.common.exception.quiz.QuizException;
import com.codingland.common.exception.user.UserErrorCode;
import com.codingland.common.exception.user.UserException;
import com.codingland.domain.dailyQuestProgress.entity.DailyQuestProgress;
import com.codingland.domain.dailyQuestProgress.repository.DailyQuestProgressRepository;
import com.codingland.domain.quiz.dto.ResponseIsQuizClearedDto;
import com.codingland.domain.quiz.dto.ResponseIsQuizClearedListDto;
import com.codingland.domain.quiz.entity.IsQuizCleared;
import com.codingland.domain.quiz.entity.Quiz;
import com.codingland.domain.quiz.repository.IsQuizClearedRepository;
import com.codingland.domain.quiz.repository.QuizRepository;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IsQuizClearedService {
    private final QuizRepository quizRepository;
    private final IsQuizClearedRepository isQuizClearedRepository;
    private final DailyQuestProgressRepository dailyQuestProgressRepository;
    private final UserRepository userRepository;

    /**
     * 풀이가 완료된 퀴즈를 유저 정보와 함께 등록처리 합니다.
     * 풀이 완료 처리 전, 해당 퀴즈 id와 유저 Id로 등록된 ROW가 있는지 확인하고 있다면 예외처리합니다.
     * 하루 퀴즈 퀘스트 진행도가 없다면 새로운 DailyQuestProgress를 생성한뒤 저장합니다.
     * DailyQuestProgress가 존재한다면, 해결된 문제(SolvedCount) 필드에 값을 +1 하고 저장합니다.
     * DailyQuestProgress의 진행도가 3 이상이라면 퀘스트 진행도(isCompleted)를 완료로 표시합니다.
     * @author 김원정
     * @param quiz_id 퀴즈 id
     * @param user_id 유저 id
     * @throws UserException 유저가 존재하지 않을 경우 발생하는 예외입니다.
     * @throws QuizException 문제가 존재하지 않을 경우 발생하는 예외입니다.
     * @throws IsQuizClearedException 이미 풀이 완료가 처리 된 후라면 발생하는 예외입니다.
     */
    @Transactional
    public void solveProblem(Long quiz_id, Long user_id) {
            User foundUser = userRepository.findById(user_id)
                    .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
            Quiz foundQuiz = quizRepository.findById(quiz_id)
                    .orElseThrow(() -> new QuizException(QuizErrorCode.NOT_FOUND_QUIZ_ERROR));
        if (isQuizClearedRepository.findByQuizAndUser(foundQuiz, foundUser).isPresent()) {
            throw new IsQuizClearedException(IsQuizClearedErrorCode.ALREADY_EXIST);
        }
            IsQuizCleared newIsQuizCleared = IsQuizCleared.thisProblemIsCleared(foundQuiz, foundUser);
            isQuizClearedRepository.save(newIsQuizCleared);

            LocalDate today = LocalDate.now();
            DailyQuestProgress progress = dailyQuestProgressRepository.findByUserAndDate(foundUser, today)
                    .orElseGet(() -> DailyQuestProgress.builder()
                            .user(foundUser)
                            .date(today)
                            .solvedCount(0)
                            .isCompleted(false)
                            .build());

            if (progress.getSolvedCount() < 3) {
                progress.updateSolvedCount(progress.getSolvedCount() + 1);
            }

            dailyQuestProgressRepository.save(progress);
    }

    /**
     * 퀴즈 완료 여부를 단 건 조회하는 메서드입니다.
     * @author 김원정
     * @param isQuizCleared_id 퀴즈 완료 여부의 id
     * @throws IsQuizClearedException 퀴즈 완료 여부가 존재하지 않을 경우 발생하는 예외입니다.
     */
    @Transactional(readOnly = true)
    public ResponseIsQuizClearedDto getIsQuizCleared(Long isQuizCleared_id) {
        IsQuizCleared foundIsQuizCleared = isQuizClearedRepository.findById(isQuizCleared_id)
                .orElseThrow(() -> new IsQuizClearedException(IsQuizClearedErrorCode.NOT_FOUND_QUIZ_ERROR));
        return new ResponseIsQuizClearedDto(
                foundIsQuizCleared.getId(),
                foundIsQuizCleared.getUser().getUserId(),
                foundIsQuizCleared.getQuiz().getId(),
                foundIsQuizCleared.isCleared()
        );
    }

    /**
     * 데이터베이스에 등록된 퀴즈 완료 여부를 모두 조회하는 메서드입니다.
     * @author 김원정
     */
    @Transactional(readOnly = true)
    public ResponseIsQuizClearedListDto getAllIsQuizCleared() {
        List<IsQuizCleared> isQuizClearedList = isQuizClearedRepository.findAll();
        List<ResponseIsQuizClearedDto> responseIsQuizClearedDtoList = new ArrayList<>();
        for (IsQuizCleared isQuizCleared : isQuizClearedList) {
            responseIsQuizClearedDtoList.add(
                    new ResponseIsQuizClearedDto(
                            isQuizCleared.getId(),
                            isQuizCleared.getUser().getUserId(),
                            isQuizCleared.getQuiz().getId(),
                            isQuizCleared.isCleared()
                    )
            );
        }
        return new ResponseIsQuizClearedListDto(responseIsQuizClearedDtoList);
    }

    /**
     * 퀴즈 완료 여부를 수정하는 메서드입니다.
     * @author 김원정
     * @param isQuizCleared_id 퀴즈 완료 여부 id
     * @param is_cleared 완료 여부
     * @throws IsQuizClearedException 문제 완료 여부가 존재하지 않을 경우 발생하는 예외입니다.
     */
    @Transactional
    public void editIsQuizCleared(Long isQuizCleared_id, boolean is_cleared) {
        IsQuizCleared foundIsQuizCleared = isQuizClearedRepository.findById(isQuizCleared_id)
                .orElseThrow(() -> new IsQuizClearedException(IsQuizClearedErrorCode.NOT_FOUND_QUIZ_ERROR));
        foundIsQuizCleared.changeIsQuizCleared(is_cleared);
    }

}
