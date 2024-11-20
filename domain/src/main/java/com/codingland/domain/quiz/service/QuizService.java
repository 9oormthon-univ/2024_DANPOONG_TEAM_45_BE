package com.codingland.domain.quiz.service;

import com.codingland.common.exception.chapter.ChapterErrorCode;
import com.codingland.common.exception.chapter.ChapterException;
import com.codingland.common.exception.quiz.DifficultyErrorCode;
import com.codingland.common.exception.quiz.DifficultyException;
import com.codingland.common.exception.quiz.QuizErrorCode;
import com.codingland.common.exception.quiz.QuizException;
import com.codingland.common.exception.user.UserErrorCode;
import com.codingland.common.exception.user.UserException;
import com.codingland.domain.chapter.entity.Chapter;
import com.codingland.domain.chapter.repository.ChapterRepository;
import com.codingland.domain.quiz.dto.*;
import com.codingland.domain.quiz.entity.Difficulty;
import com.codingland.domain.quiz.entity.IsQuizCleared;
import com.codingland.domain.quiz.entity.Quiz;
import com.codingland.domain.quiz.repository.DifficultyRepository;
import com.codingland.domain.quiz.repository.IsQuizClearedRepository;
import com.codingland.domain.quiz.repository.QuizRepository;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final ChapterRepository chapterRepository;
    private final DifficultyRepository difficultyRepository;
    private final IsQuizClearedRepository isQuizClearedRepository;
    private final UserRepository userRepository;

    /**
     * 퀴즈 생성 요청을 처리하는 메서드.
     *
     * @author 김원정
     * @param requestCreateQuizDto 퀴즈 생성 요청 DTO
     * @throws ChapterException 챕터가 존재하지 않을 경우 발생하는 예외입니다.
     * @throws DifficultyException 난이도가 존재하지 않을 경우 발생하는 예외입니다.
     */
    public void createQuiz(RequestCreateQuizDto requestCreateQuizDto) {
        Chapter foundChapter = chapterRepository.findById(requestCreateQuizDto.chapterId())
                .orElseThrow(() -> new ChapterException(ChapterErrorCode.NOT_FOUND_CHAPTER_ERROR));
        Difficulty difficulty = difficultyRepository.findByLevel(requestCreateQuizDto.level())
                .orElseThrow(() -> new DifficultyException(DifficultyErrorCode.NOT_FOUND_DIFFICULTY_ERROR));
        Quiz newQuiz = Quiz.builder()
                .type(requestCreateQuizDto.type())
                .title(requestCreateQuizDto.title())
                .answer(requestCreateQuizDto.answer())
                .chapter(foundChapter)
                .question(requestCreateQuizDto.question())
                .difficulty(difficulty)
                .build();
        quizRepository.save(newQuiz);
    }

    /**
     * 주어진 ID에 해당하는 퀴즈를 조회하여 반환합니다.
     * 사용자의 ID를 받아 퀴즈 완료 여부를 함께 파악합니다.
     *
     * @author 김원정
     * @param quiz_id 조회할 퀴즈의 ID
     * @param user_id 완료 여부를 파악할 사용자의 ID
     * @return 조회된 퀴즈 정보를 담은 ResponseFindQuizDto 객체
     * @throws QuizException 퀴즈를 찾지 못했을 경우 발생하는 예외
     * @throws UserException 유저를 찾지 못했을 경우 발생하는 예외입니다.
     */
    public ResponseQuizDto findByOne(Long quiz_id, Long user_id) {
        Quiz foundQuiz = quizRepository.findById(quiz_id)
                .orElseThrow(() -> new QuizException(QuizErrorCode.NOT_FOUND_QUIZ_ERROR));
        User foundUser = userRepository.findById(user_id)
                .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
        IsQuizCleared foundIsQuizCleared = isQuizClearedRepository.findByQuizAndUser(foundQuiz, foundUser)
                .orElse(null);
        return ResponseQuizDto.builder()
                .quizId(foundQuiz.getId())
                .chapterId(foundQuiz.getChapter().getId())
                .question(foundQuiz.getQuestion())
                .answer(foundQuiz.getAnswer())
                .title(foundQuiz.getTitle())
                .type(foundQuiz.getType())
                .level(foundQuiz.getDifficulty().getLevel())
                .isCleared(foundIsQuizCleared != null && foundIsQuizCleared.isCleared())
                .build();
    }

    /**
     * 데이터베이스에 등록된 모든 Quiz 정보를 가져오는 메서드
     *
     * @author 김원정
     * @return 조회된 Quiz들의 정보를 담고 있는 ResponseQuizListDto
     */
    public ResponseQuizListDto findByMany() {
        List<Quiz> quizList = quizRepository.findAll();
        List<ResponseQuizDto> responseQuizDtoList = new ArrayList<>();
        for (Quiz quiz : quizList) {
            responseQuizDtoList.add(
                    ResponseQuizDto.builder()
                            .quizId(quiz.getId())
                            .title(quiz.getTitle())
                            .type(quiz.getType())
                            .chapterId(quiz.getChapter().getId())
                            .answer(quiz.getAnswer())
                            .question(quiz.getQuestion())
                            .level(quiz.getDifficulty().getLevel())
                            .build()
            );
        }
        return new ResponseQuizListDto(responseQuizDtoList);
    }

    /**
     * 특정 Quiz를 수정하거나 Chapter와의 연관관계를 수정하고 싶을 때 사용하는 메서드입니다.
     *
     * @author 김원정
     * @param requestEditQuizDto 수정할 정보를 담고 있는 RequestEditQuizDto
     * @throws QuizException 퀴즈를 찾지 못했을 때 발생하는 예외입니다.
     */
    public void editQuiz(RequestEditQuizDto requestEditQuizDto) {
        Quiz foundQuiz = quizRepository.findById(requestEditQuizDto.quizId())
                .orElseThrow(() -> new QuizException(QuizErrorCode.NOT_FOUND_QUIZ_ERROR));

        Chapter foundChapter = (requestEditQuizDto.chapterId() != null) ?
                chapterRepository.findById(requestEditQuizDto.chapterId()).orElse(null) :
                foundQuiz.getChapter();

        Difficulty foundDifficulty = (requestEditQuizDto.level() != 0) ?
                difficultyRepository.findByLevel(requestEditQuizDto.level()).orElse(null) :
                foundQuiz.getDifficulty();

        foundQuiz.updateQuizByDto(requestEditQuizDto, foundChapter, foundDifficulty);
        quizRepository.save(foundQuiz);
    }

    /**
     * 퀴즈를 삭제할 때 사용하는 메서드입니다.
     *
     * @author 김원정
     * @param quiz_id 삭제하고 싶은 quiz의 id
     * @throws QuizException 퀴즈를 찾지 못했을 때 발생하는 예외입니다.
     */
    public void deleteQuiz(Long quiz_id) {
        Quiz foundQuiz = quizRepository.findById(quiz_id)
                .orElseThrow(() -> new QuizException(QuizErrorCode.NOT_FOUND_QUIZ_ERROR));
        quizRepository.delete(foundQuiz);
    }
}
