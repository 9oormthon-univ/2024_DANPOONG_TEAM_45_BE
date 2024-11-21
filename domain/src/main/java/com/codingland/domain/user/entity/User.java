package com.codingland.domain.user.entity;

import com.codingland.common.common.BaseEntity;
import com.codingland.domain.user.dto.request.EditUserRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Access(AccessType.FIELD)
public class User extends BaseEntity {
    private static final String DEFAULT_NICKNAME = "사용자";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String subId;
    private String name;
    private String picture;
    private String email;
    private int activityPoint = 0;
    private boolean isTrainingComplete = false;


    public static User createSocialUser(String subId, String name, String picture, String email) {
        User user = new User();
        user.subId = subId;
        user.name = name;
        user.email = email;
        user.picture = picture;
        return user;
    }

    public void editUser(EditUserRequest editUserRequest) {
        if (editUserRequest.subId() != null) this.subId = editUserRequest.subId();
        if (editUserRequest.name() != null) this.name = editUserRequest.name();
        if (editUserRequest.email() != null) this.email = editUserRequest.email();
        if (editUserRequest.picture() != null) this.picture = editUserRequest.picture();
    }

    public void increaseActivityPoint(int point) {
        this.activityPoint += point;
    }

    public void decreaseActivityPoint(int point) {
        this.activityPoint -= point;
    }

    public void completeBasicTraining() {
        this.isTrainingComplete = true;
    }
}
