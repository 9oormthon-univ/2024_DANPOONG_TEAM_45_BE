package com.codingland.domain.character.common;

import lombok.Getter;

@Getter
public enum CactusType {
    KING_CACTUS("킹 선인장", "★"),
    HERO_CACTUS("영웅 선인장", "★★"),
    MAGICIAN("마법사 선인장", "★★★");

    private final String name;
    private final String rank;

    CactusType(String name, String rank) {
        this.name = name;
        this.rank = rank;
    }
}
