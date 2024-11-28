package com.codingland.domain.quiz.entity;

import com.codingland.domain.quiz.common.BlockTypeEnum;
import com.codingland.domain.quiz.dto.RequestEditAnswerDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Getter
@Entity
@BatchSize(size = 10)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BlockTypeEnum type;

    private String msg;

    @Column(name = "repeat_count")
    private int repeat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUIZ_ID")
    private Quiz quiz;

    @Builder
    public Answer(String msg, BlockTypeEnum type, int repeat, Quiz quiz) {
        this.msg = msg;
        this.type = type;
        this.quiz = quiz;
        this.repeat = repeat;
    }

    public void editAnswer(RequestEditAnswerDto requestEditAnswerDto) {
        if (requestEditAnswerDto.msg() != null) this.msg = requestEditAnswerDto.msg();
        if (requestEditAnswerDto.type() != null) this.type = requestEditAnswerDto.type();
        if (requestEditAnswerDto.repeat() != 0) this.repeat = requestEditAnswerDto.repeat();
    }
}
