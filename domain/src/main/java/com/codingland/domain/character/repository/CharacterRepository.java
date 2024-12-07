package com.codingland.domain.character.repository;

import com.codingland.domain.character.entity.Character;
import com.codingland.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findCharacterByUser(User user);
}
