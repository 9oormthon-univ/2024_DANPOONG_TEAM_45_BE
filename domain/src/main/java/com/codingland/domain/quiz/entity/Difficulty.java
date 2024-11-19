package com.codingland.domain.quiz.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Difficulty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIFFICULTY_ID")
    private Long id;
    @Column(unique = true, nullable = false)
    private int level;

    public Difficulty(int level) {
        this.level = level;
    }

    public void updateDifficulty(int difficulty) {
        this.level = difficulty;
    }
}
