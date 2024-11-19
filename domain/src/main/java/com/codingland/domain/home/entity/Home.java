package com.codingland.domain.home.entity;

import com.codingland.domain.home.dto.RequestEditHomeDto;
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

    @Column(name = "character_id", nullable = true)
    private Long characterId; // 캐릭터 ID

    @Column(name = "character_type", nullable = true)
    private String characterType; // 캐릭터 타입

    @Column(name = "character_name", nullable = true)
    private String characterName; // 캐릭터 이름

    // 커스텀 생성자
    public Home(Long id,Long characterId, String characterType, String characterName) {
        this.id = id;
        this.characterId = characterId;
        this.characterType = characterType;
        this.characterName = characterName;
    }

    public void editHome(RequestEditHomeDto requestEditHomeDto) {
        this.id = requestEditHomeDto.id();
    }
}
