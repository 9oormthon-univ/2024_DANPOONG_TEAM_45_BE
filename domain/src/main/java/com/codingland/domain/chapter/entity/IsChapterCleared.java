package com.codingland.domain.chapter.entity;

import com.codingland.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IsChapterCleared {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ColumnDefault("false")
    private boolean isCleared;

    @ManyToOne
    @JoinColumn(name = "CHAPTER_ID")
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name = "USER_USERID")
    private User user;

    public IsChapterCleared(boolean isCleared, Chapter chapter, User user) {
        this.isCleared = isCleared;
        this.chapter = chapter;
        this.user = user;
    }

    public static IsChapterCleared thisChapterIsCleared(Chapter chapter, User user) {
        return new IsChapterCleared(true, chapter, user);
    }

    public void changeIsCleared(boolean isCleared) {
        this.isCleared = isCleared;
    }
}
