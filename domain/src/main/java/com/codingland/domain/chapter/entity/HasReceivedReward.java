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
public class HasReceivedReward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ColumnDefault("false")
    private boolean hasReceived;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAPTER_ID")
    private Chapter chapter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_USERID")
    private User user;

    public HasReceivedReward(boolean hasReceived, Chapter chapter, User user) {
        this.hasReceived = hasReceived;
        this.chapter = chapter;
        this.user = user;
    }

    public static HasReceivedReward thisRewardHasReceived(Chapter chapter, User user) {
        return new HasReceivedReward(true, chapter, user);
    }
}
