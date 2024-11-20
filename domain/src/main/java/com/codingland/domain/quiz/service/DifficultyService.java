package com.codingland.domain.quiz.service;

import com.codingland.common.exception.quiz.DifficultyErrorCode;
import com.codingland.common.exception.quiz.DifficultyException;
import com.codingland.common.exception.quiz.QuizErrorCode;
import com.codingland.common.exception.quiz.QuizException;
import com.codingland.domain.quiz.dto.ResponseDifficultyDto;
import com.codingland.domain.quiz.dto.ResponseDifficultyListDto;
import com.codingland.domain.quiz.entity.Difficulty;
import com.codingland.domain.quiz.entity.Quiz;
import com.codingland.domain.quiz.repository.DifficultyRepository;
import com.codingland.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DifficultyService {
    private final DifficultyRepository difficultyRepository;
    private final QuizRepository quizRepository;

    /**
     *  난이도를 등록할 때 사용하는 메서드.
     *
     * @author 김원정
     * @param level 난이도
     */
    public void createDifficulty(int level) {
        Difficulty newDifficulty = new Difficulty(level);
        difficultyRepository.save(newDifficulty);
    }

    /**
     * 데이터베이스에 등록된 난이도를 모두 조회할 때 사용하는 메서드.
     * @author 김원정
     */
    public ResponseDifficultyListDto getDifficultyList() {
        List<Difficulty> difficultyList = difficultyRepository.findAll();
        List<ResponseDifficultyDto> difficultyDtoList = new ArrayList<>();
        for (Difficulty difficulty : difficultyList) {
            difficultyDtoList.add(
                    new ResponseDifficultyDto(
                            difficulty.getId(),
                            difficulty.getLevel()
                    )
            );
        }
        return new ResponseDifficultyListDto(difficultyDtoList);
    }

    /**
     * 난이도 수치를 조절할 때 사용하는 메서드입니다.
     * @author 김원정
     * @param difficulty_id 난이도 id
     * @param level 조절할 난이도 값
     * @throws DifficultyException 난이도가 존재하지 않을 경우 발생하는 예외입니다.
     */
    public void editDifficulty(Long difficulty_id, int level) {
        Difficulty foundDifficulty = difficultyRepository.findById(difficulty_id)
                .orElseThrow(() -> new DifficultyException(DifficultyErrorCode.NOT_FOUND_DIFFICULTY_ERROR));
        foundDifficulty.updateDifficulty(level);
        difficultyRepository.save(foundDifficulty);
    }

    /**
     * 난이도를 삭제할 때 사용합니다. 퀴즈와 연관관계가 맺어진 난이도라면 삭제될 수 없습니다.
     * @author 김원정
     * @param difficulty_id 난이도 id
     * @throws DifficultyException 난이도가 존재하지 않을 경우 발생하는 예외입니다.
     * @throws QuizException 퀴즈에 사용 중인 난이도의 경우(연관관계가 맺어져 있는 경우) 삭제되지 않아 발생하는 예외입니다.
     */
    public void deleteDifficulty(Long difficulty_id) {
        Difficulty foundDifficulty = difficultyRepository.findById(difficulty_id)
                .orElseThrow(() -> new DifficultyException(DifficultyErrorCode.NOT_FOUND_DIFFICULTY_ERROR));
        List<Quiz> foundQuizzes = quizRepository.findAllByDifficulty(foundDifficulty);
        if (!foundQuizzes.isEmpty()) {
            throw new QuizException(QuizErrorCode.QUIZ_HAS_RELATED_DIFFICULTY);
        }
        difficultyRepository.delete(foundDifficulty);
    }


}
