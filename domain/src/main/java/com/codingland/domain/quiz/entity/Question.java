package com.codingland.domain.quiz.entity;

import com.codingland.domain.quiz.common.BlockTypeEnum;
import com.codingland.domain.quiz.dto.RequestEditQuestionDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BlockTypeEnum type;

    private String msg;

    @Column(name = "repeat_count")
    private int repeat;

    @ManyToOne
    @JoinColumn(name = "QUIZ_ID")
    private Quiz quiz;

    @Builder
    public Question(BlockTypeEnum type, String msg, int repeat, Quiz quiz) {
        this.type = type;
        this.msg = msg;
        this.repeat = repeat;
        this.quiz = quiz;
    }

    public void editQuestion(RequestEditQuestionDto requestEditQuestionDto) {
        if (requestEditQuestionDto.msg() != null) this.msg = requestEditQuestionDto.msg();
        if (requestEditQuestionDto.type() != null) this.type = requestEditQuestionDto.type();
        if (requestEditQuestionDto.repeat() != 0) this.repeat = requestEditQuestionDto.repeat();
    }
}
