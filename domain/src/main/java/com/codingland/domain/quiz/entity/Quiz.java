package com.codingland.domain.quiz.entity;

import com.codingland.domain.chapter.entity.Chapter;
import com.codingland.domain.quiz.dto.RequestEditQuizDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUIZ_ID")
    private Long id;
    private String title;
    private String message;
    private String hint;

    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz")
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "CHAPTER_ID")
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name = "DIFFICULTY_ID")
    private Difficulty difficulty;

    @Builder
    public Quiz(List<Question> questions, List<Answer> answers, String title, Chapter chapter,
                Difficulty difficulty, String message, String hint) {
        this.questions = questions;
        this.answers = answers;
        this.title = title;
        this.chapter = chapter;
        this.difficulty = difficulty;
        this.message = message;
        this.hint = hint;
    }

    public void updateQuizByDto(RequestEditQuizDto requestEditQuizDto, Chapter chapter, Difficulty difficulty) {
        if (requestEditQuizDto.title() != null) this.title = requestEditQuizDto.title();
        if (requestEditQuizDto.message() != null) this.message = requestEditQuizDto.message();
        if (chapter != null) this.chapter = chapter;
        if (difficulty != null) this.difficulty = difficulty;
        if (requestEditQuizDto.hint() != null) this.hint = requestEditQuizDto.hint();
    }
}
