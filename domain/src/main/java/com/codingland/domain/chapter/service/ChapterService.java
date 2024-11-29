package com.codingland.domain.chapter.service;

import com.codingland.common.exception.chapter.ChapterErrorCode;
import com.codingland.common.exception.chapter.ChapterException;
import com.codingland.common.exception.user.UserErrorCode;
import com.codingland.common.exception.user.UserException;
import com.codingland.domain.chapter.dto.RequestChapterDto;
import com.codingland.domain.chapter.dto.RequestEditChapterDto;
import com.codingland.domain.chapter.dto.ResponseChapterDto;
import com.codingland.domain.chapter.dto.ResponseChapterListDto;
import com.codingland.domain.chapter.entity.Chapter;
import com.codingland.domain.chapter.entity.HasReceivedReward;
import com.codingland.domain.chapter.entity.IsChapterCleared;
import com.codingland.domain.chapter.repository.ChapterRepository;
import com.codingland.domain.chapter.repository.HasReceivedRewardRepository;
import com.codingland.domain.chapter.repository.IsChapterClearedRepository;
import com.codingland.domain.quiz.dto.ResponseFindQuizByChapter;
import com.codingland.domain.quiz.entity.IsQuizCleared;
import com.codingland.domain.quiz.entity.Quiz;
import com.codingland.domain.quiz.repository.IsQuizClearedRepository;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final IsChapterClearedRepository isChapterClearedRepository;
    private final IsQuizClearedRepository isQuizClearedRepository;
    private final HasReceivedRewardRepository hasReceivedRewardRepository;
    private final UserRepository userRepository;

    /**
     * 챕터를 등록하는 메서드입니다.
     * @author 김원정
     * @param requestChapterDto 챕터 등록 Dto
     */
    @Transactional
    public void createChapter(RequestChapterDto requestChapterDto) {
        Chapter newChapter = new Chapter(requestChapterDto.name());
        chapterRepository.save(newChapter);
    }

    /**
     * 챕터를 단 건 조회하는 메서드입니다.
     * 사용자의 id를 받아, 완료 여부를 함께 조회합니다.
     *
     * @author 김원정
     * @param chapter_id 챕터의 id
     * @param user_id 조회될 사용자의 id
     * @throws UserException 유저가 조회되지 않을 경우 생기는 예외
     * @throws ChapterException 챕터가 조회되지 않을 경우 생기는 예외
     */
    @Transactional(readOnly = true)
    public ResponseChapterDto getChapter(Long chapter_id, Long user_id) {
        Chapter foundChapter = chapterRepository.findById(chapter_id)
                .orElseThrow(() -> new ChapterException(ChapterErrorCode.NOT_FOUND_CHAPTER_ERROR));
        User foundUser = userRepository.findById(user_id)
                .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
        IsChapterCleared foundIsChapterCleared = isChapterClearedRepository.findByChapterAndUser(foundChapter, foundUser)
                .orElse(null);

        List<Long> quizIds = new ArrayList<>();
        for (Quiz quiz : foundChapter.getQuizzes()) {
            quizIds.add(quiz.getId());
        }

        List<IsQuizCleared> clearedQuizzes = isQuizClearedRepository.findAllByUserAndQuizIn(foundUser, foundChapter.getQuizzes());
        Set<Long> clearedQuizzesIds = new HashSet<>();

        for (IsQuizCleared clearedQuiz : clearedQuizzes) {
            clearedQuizzesIds.add(clearedQuiz.getId());
        }

        boolean buttonActiveState = true;

        for (Long quizId: quizIds) {
            if (!clearedQuizzesIds.contains(quizId)) {
                buttonActiveState = false;
                break;
            }
        }
        
        HasReceivedReward hasReceivedReward = hasReceivedRewardRepository.findByChapterAndUser(foundChapter, foundUser)
                .orElse(null);

        if(buttonActiveState && hasReceivedReward != null) {
                buttonActiveState = false;
        }

        if(foundChapter.getQuizzes().isEmpty()) {
            buttonActiveState = false;
        }

        Map<Long, IsQuizCleared> quizClearedMap = new HashMap<>();

        for (IsQuizCleared clearedQuiz : clearedQuizzes) {
            quizClearedMap.put(clearedQuiz.getQuiz().getId(), clearedQuiz);
        }

        List<ResponseFindQuizByChapter> responseQuizDtoList = new ArrayList<>();
        for (Quiz quiz : foundChapter.getQuizzes()) {
            IsQuizCleared cleared = quizClearedMap.get(quiz.getId());
            responseQuizDtoList.add(
              ResponseFindQuizByChapter.builder()
                      .quizId(quiz.getId())
                      .level(quiz.getDifficulty().getLevel())
                      .title(quiz.getTitle())
                      .isCleared(cleared != null && cleared.isCleared())
                      .build()
            );
        }

        return ResponseChapterDto.builder()
                .id(foundChapter.getId())
                .name(foundChapter.getName())
                .isCleared(foundIsChapterCleared != null && foundIsChapterCleared.isCleared())
                .quizzes(responseQuizDtoList)
                .isRewardButtonActive(buttonActiveState)
                .build();
    }

    /**
     * 데이터베이스에 등록된 챕터를 모두 조회하는 메서드입니다.
     * @author 김원정
     */
    @Transactional(readOnly = true)
    public ResponseChapterListDto getChapterList(Long user_id) {
        List<Chapter> foundChapterList = chapterRepository.findAllWithQuizzes();
        User foundUser = userRepository.findById(user_id)
                .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
        List<IsChapterCleared> isChapterClearedList = isChapterClearedRepository.findAllByUserAndChapterIn(foundUser, foundChapterList);
        Map<Long, IsChapterCleared> isChapterClearedMap = new HashMap<>();
        for (IsChapterCleared isChapterCleared : isChapterClearedList) {
            isChapterClearedMap.put(isChapterCleared.getChapter().getId(), isChapterCleared);
        }
        List<ResponseChapterDto> responseChapterDtoList = new ArrayList<>();
        List<Quiz> allQuizzes = new ArrayList<>();
        for (Chapter chapter : foundChapterList) {
            allQuizzes.addAll(chapter.getQuizzes());
        }
        List<IsQuizCleared> isQuizClearedList = isQuizClearedRepository.findAllByUserAndQuizIn(foundUser, allQuizzes);
        Map<Long, IsQuizCleared> isQuizClearedMap = new HashMap<>();
        for (IsQuizCleared isQuizCleared : isQuizClearedList) {
            isQuizClearedMap.put(isQuizCleared.getQuiz().getId(), isQuizCleared);
        }
        for (Chapter chapter : foundChapterList) {
            IsChapterCleared isChapterCleared = isChapterClearedMap.get(chapter.getId());
            List<ResponseFindQuizByChapter> responseFindQuizByChapterList = new ArrayList<>();
            if (!chapter.getQuizzes().isEmpty()) {
                for (Quiz quiz : chapter.getQuizzes()) {
                    IsQuizCleared isQuizCleared = isQuizClearedMap.get(quiz.getId());
                    responseFindQuizByChapterList.add(
                            ResponseFindQuizByChapter.builder()
                                    .isCleared(isQuizCleared != null && isQuizCleared.isCleared())
                                    .quizId(quiz.getId())
                                    .level(quiz.getDifficulty().getLevel())
                                    .title(quiz.getTitle())
                                    .build()
                    );
                }
            }
            responseChapterDtoList.add(
                    ResponseChapterDto.builder()
                            .id(chapter.getId())
                            .name(chapter.getName())
                            .isCleared(isChapterCleared != null && isChapterCleared.isCleared())
                            .quizzes(responseFindQuizByChapterList)
                            .build()
            );
        }
        return new ResponseChapterListDto(responseChapterDtoList);
    }

    /**
     * 챕터를 수정하는 메서드입니다.
     * @author 김원정
     * @param chapter_id 챕터의 id
     * @throws ChapterException 챕터가 존재하지 않을 경우 생기는 예외
     */
    @Transactional
    public void editChapter(Long chapter_id, RequestEditChapterDto requestChapterDto) {
        Chapter foundChapter = chapterRepository.findById(chapter_id)
                .orElseThrow(() -> new ChapterException(ChapterErrorCode.NOT_FOUND_CHAPTER_ERROR));
        foundChapter.editChapter(requestChapterDto);
        chapterRepository.save(foundChapter);
    }

    /**
     * 챕터 삭제하는 메서드입니다.
     * @author 김원정
     * @param chapter_id 챕터의 id
     * @throws ChapterException 챕터가 존재하지 않을 경우 생기는 예외
     */
    @Transactional
    public void deleteChapter(Long chapter_id) {
        Chapter foundChapter = chapterRepository.findById(chapter_id)
                .orElseThrow(() -> new ChapterException(ChapterErrorCode.NOT_FOUND_CHAPTER_ERROR));
        chapterRepository.delete(foundChapter);
    }
}
