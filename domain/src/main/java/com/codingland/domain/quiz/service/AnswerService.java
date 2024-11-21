package com.codingland.domain.quiz.service;

import com.codingland.common.exception.quiz.AnswerErrorCode;
import com.codingland.common.exception.quiz.AnswerException;
import com.codingland.domain.quiz.dto.RequestEditAnswerDto;
import com.codingland.domain.quiz.entity.Answer;
import com.codingland.domain.quiz.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    /**
     * Answer를 찾아 수정하는 메서드입니다.
     * @author 김원정
     * @param requestEditAnswerDto 정답 수정 Dto
     * @throws AnswerException 수정 요청된 정답이 존재하지 않을 때 발생합니다.
     */
    public void editAnswer(RequestEditAnswerDto requestEditAnswerDto) {
        Answer foundAnswer = answerRepository.findById(requestEditAnswerDto.answerId())
                .orElseThrow(() -> new AnswerException(AnswerErrorCode.NOT_FOUND_ANSWER_ERROR));

        foundAnswer.editAnswer(requestEditAnswerDto);
        answerRepository.save(foundAnswer);
    }


    /**
     * Answer를 찾아 삭제하는 메서드입니다.
     * @author 김원정
     * @param answer_id 정답 id
     */
    public void deleteAnswer(Long answer_id) {
        answerRepository.deleteById(answer_id);
    }
}
