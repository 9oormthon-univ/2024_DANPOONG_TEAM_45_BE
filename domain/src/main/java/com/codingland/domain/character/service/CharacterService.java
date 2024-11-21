package com.codingland.domain.character.service;

import com.codingland.common.exception.character.CharacterErrorCode;
import com.codingland.common.exception.character.CharacterException;
import com.codingland.common.exception.user.UserErrorCode;
import com.codingland.common.exception.user.UserException;
import com.codingland.domain.character.dto.RequestCharacterDto;
import com.codingland.domain.character.dto.ResponseCreateCharacterDto;
import com.codingland.domain.character.entity.Character;
import com.codingland.domain.character.repository.CharacterRepository;
import com.codingland.domain.home.entity.Home;
import com.codingland.domain.home.repository.HomeRepository;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final HomeRepository homeRepository;
    private final UserRepository userRepository;

    /**
     * 캐릭터를 등록하는 메서드입니다.
     * 새로운 캐릭터를 생성한 뒤 캐릭터와 연관관계가 맺어진 Home을 생성합니다.
     * @author 김원정
     * @param requestCharacterDto 캐릭터 등록 요청 Dto
     * @param user_id 홈과 연관관계를 맺을 유저의 id
     * @return 새로운 캐릭터의 id와 캐릭터와 연관관계가 맺어진 새로운 home을 반환합니다.
     * @throws UserException 유저를 찾지 못했을 때 반환되는 예외
     */
    public ResponseCreateCharacterDto createCharacter(Long user_id, RequestCharacterDto requestCharacterDto) {
        Character newCharacter = new Character(requestCharacterDto.name());
        Character savedCharacter = characterRepository.save(newCharacter);
        User foundUser = userRepository.findById(user_id)
                .orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
        Home newHome = new Home(foundUser, savedCharacter);
        Home savedHome = homeRepository.save(newHome);
        return new ResponseCreateCharacterDto(savedCharacter.getId(), savedHome.getId());
    }

    /**
     * 캐릭터의 이름을 수정하는 메서드입니다.
     * @author 김원정
     * @param name 캐릭터의 수정될 이름
     * @param character_id 캐릭터 id
     * @throws CharacterException 수정될 캐릭터를 찾지 못했을 경우 발생하는 Exception
     */
    public void editNameCharacter(Long character_id, String name) {
        Character foundCharacter = characterRepository.findById(character_id)
                .orElseThrow(() -> new CharacterException(CharacterErrorCode.NOT_FOUND_CHARACTER_ERROR));
        foundCharacter.editName(name);
        characterRepository.save(foundCharacter);
    }

    /**
     * 캐릭터의 활동 포인트를 증가시키는 메서드입니다.
     * @author 김원정
     * @param character_id 활동 포인트가 증가될 캐릭터
     * @param increased_point 증가될 활동 포인트
     * @throws CharacterException 활동 포인트가 증가될 캐릭터를 찾지 못했을 경우 발생하는 Exception
     */
    public void increasedPoint(Long character_id, int increased_point) {
        Character foundCharacter = characterRepository.findById(character_id)
                .orElseThrow(() -> new CharacterException(CharacterErrorCode.NOT_FOUND_CHARACTER_ERROR));
        foundCharacter.increaseActivityPoints(increased_point);
        characterRepository.save(foundCharacter);
    }

    /**
     * 캐릭터의 활동 포인트를 감소시키는 메서드입니다.
     * @author 김원정
     * @param character_id 활동 포인트가 감소될 캐릭터
     * @param decreased_point 감소될 활동 포인트
     * @throws CharacterException 활동 포인트가 감소될 캐릭터를 찾지 못했을 경우 발생하는 Exception
     */
    public void decreasedPoint(Long character_id, int decreased_point) {
        Character foundCharacter = characterRepository.findById(character_id)
                .orElseThrow(() -> new CharacterException(CharacterErrorCode.NOT_FOUND_CHARACTER_ERROR));
        foundCharacter.decreaseActivityPoints(decreased_point);
        characterRepository.save(foundCharacter);
    }


}
