package com.codingland.domain.character.repository;

import com.codingland.domain.character.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
