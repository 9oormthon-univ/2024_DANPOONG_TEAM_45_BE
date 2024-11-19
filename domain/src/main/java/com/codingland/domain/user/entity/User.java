package com.codingland.domain.user.entity;

import com.codingland.common.common.BaseEntity;
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


    public static User createSocialUser(String subId, String name, String picture, String email) {

        User user = new User();
        user.subId = subId;
        user.name = name;
        user.email = email;
        user.picture = picture;
        return user;
    }
}
