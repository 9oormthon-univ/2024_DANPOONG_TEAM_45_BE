package com.codingland.domain.character.entity;

import com.codingland.domain.character.common.CactusType;
import com.codingland.domain.character.common.ProgressEnum;
import com.codingland.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "CI_CHARACTER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character {

    @Id
    @Column(name = "CHARACTER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int level = 0;
    private ProgressEnum type = ProgressEnum.LEVEL_LOW;
    private CactusType cactus = CactusType.KING_CACTUS;
    private int activityPoints = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_id")
    private User user;

    @Builder
    public Character(String name, CactusType cactus, User user) {
        this.name = name;
        this.cactus = cactus;
        this.user = user;
    }
    public void editName(String name) {
        this.name = name;
    }

    public void increaseActivityPoints(int point) {
        this.activityPoints += point;

        int levelIncrease = this.activityPoints / 100 - this.level;
        if (levelIncrease > 0) {
            this.level += levelIncrease;
        }

        if (this.level > 10) {
            this.type = ProgressEnum.LEVEL_HIGH;
        } else if (this.level > 5) {
            this.type = ProgressEnum.LEVEL_MEDIUM;
        }
    }

    public void decreaseActivityPoints(int point) {
        this.activityPoints -= point;

        if (this.activityPoints < 0) {
            this.activityPoints = 0;
        }

        int newLevel = this.activityPoints / 100;
        if (newLevel < this.level) {
            this.level = newLevel;

            if (this.level > 10) {
                this.type = ProgressEnum.LEVEL_HIGH;
            } else if (this.level > 5) {
                this.type = ProgressEnum.LEVEL_MEDIUM;
            } else {
                this.type = ProgressEnum.LEVEL_LOW;
            }
        }
    }

}
