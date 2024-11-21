package com.codingland.domain.home.entity;

import com.codingland.domain.character.entity.Character;
import com.codingland.domain.home.dto.RequestEditHomeDto;
import com.codingland.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Home {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 홈 ID

    @OneToOne
    @JoinColumn(name = "CHARACTER_ID")
    private Character character;

    @OneToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    public Home(User user, Character character) {
        this.user = user;
        this.character = character;
    }

    public void editHome(RequestEditHomeDto requestEditHomeDto) {
        this.id = requestEditHomeDto.id();
    }
}
