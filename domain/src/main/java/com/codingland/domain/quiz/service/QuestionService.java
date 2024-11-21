package com.codingland.domain.quiz.service;

import com.codingland.common.exception.quiz.QuestionErrorCode;
import com.codingland.common.exception.quiz.QuestionException;
import com.codingland.domain.quiz.dto.RequestEditQuestionDto;
import com.codingland.domain.quiz.entity.Question;
import com.codingland.domain.quiz.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    /**
     * Question을 찾아 수정하는 메서드입니다.
     * @author 김원정
     * @param requestEditQuestionDto 질문 수정 Dto
     * @throws QuestionException 수정 요청된 질문이 존재하지 않을 때 발생합니다.
     */
    public void editQuestion(RequestEditQuestionDto requestEditQuestionDto) {
        Question foundQuestion = questionRepository.findById(requestEditQuestionDto.answerId())
                .orElseThrow(() -> new QuestionException(QuestionErrorCode.NOT_FOUND_QUESTION_ERROR));
        foundQuestion.editQuestion(requestEditQuestionDto);
        questionRepository.save(foundQuestion);
    }

    /**
     * Question을 찾아 삭제하는 메서드입니다.
     * @author 김원정
     * @param question_id 문제 id
     */
    public void deleteQuestion(Long question_id) {
        questionRepository.deleteById(question_id);
    }
}
