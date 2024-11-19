package com.codingland.domain.quiz.entity;

import com.codingland.domain.chapter.entity.Chapter;
import com.codingland.domain.quiz.common.QuizTypeEnum;
import com.codingland.domain.quiz.dto.RequestEditQuizDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUIZ_ID")
    private Long id;
    private String question;
    private String answer;
    private QuizTypeEnum type;
    private String title;

    @ManyToOne
    @JoinColumn(name = "CHAPTER_ID")
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name = "DIFFICULTY_ID")
    private Difficulty difficulty;

    @Builder
    public Quiz(String question, String answer, QuizTypeEnum type, String title, Chapter chapter,
                Difficulty difficulty) {
        this.question = question;
        this.answer = answer;
        this.type = type;
        this.title = title;
        this.chapter = chapter;
        this.difficulty = difficulty;
    }

    public void updateQuizByDto(RequestEditQuizDto requestEditQuizDto, Chapter chapter, Difficulty difficulty) {
        if (requestEditQuizDto.question() != null) this.question = requestEditQuizDto.question();
        if (requestEditQuizDto.answer() != null) this.answer = requestEditQuizDto.answer();
        if (requestEditQuizDto.type() != null) this.type = requestEditQuizDto.type();
        if (requestEditQuizDto.title() != null) this.title = requestEditQuizDto.title();
        if (chapter != null) this.chapter = chapter;
        if (difficulty != null) this.difficulty = difficulty;
    }
}
