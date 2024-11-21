package com.codingland.apiservice.character.presentation;

import com.codingland.common.common.ApplicationResponse;
import com.codingland.domain.character.dto.RequestCharacterDto;
import com.codingland.domain.character.dto.ResponseCreateCharacterDto;
import com.codingland.domain.character.service.CharacterService;
import com.codingland.domain.user.entity.User;
import com.codingland.security.annotation.UserResolver;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/character")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @PostMapping
    @Operation(summary = "캐릭터 등록", description = """
            (사용자) 캐릭터를 등록합니다.
            """)
    public ApplicationResponse<ResponseCreateCharacterDto> createCharacter(
            @RequestBody RequestCharacterDto requestCharacterDto,
            @UserResolver User user) {
        ResponseCreateCharacterDto result = characterService.createCharacter(user.getUserId(), requestCharacterDto);
        return ApplicationResponse.ok(result);
    }

    @PutMapping("/{character_id}")
    @Operation(summary = "캐릭터 이름 수정", description = """
            (사용자) 캐릭터의 이름을 수정합니다.
            """)
    public ApplicationResponse<Void> updateNameCharacter(@PathVariable Long character_id, @RequestParam String name) {
        characterService.editNameCharacter(character_id, name);
        return ApplicationResponse.ok(null);
    }
    @PostMapping("/increaseActivityPoint")
    @Operation(summary = "활동 포인트 증가", description = """
            (사용자) 캐릭터의 활동 포인트를 증가시킵니다.
            """)
    public ApplicationResponse<Void> increasedCharacter(@RequestParam Long character_id, @RequestParam Integer activityPoint) {
        characterService.increasedPoint(character_id, activityPoint);
        return ApplicationResponse.ok(null);
    }

    @PostMapping("/decreaseActivityPoint")
    @Operation(summary = "활동 포인트 감소", description = """
            (사용자) 캐릭터의 활동 포인트를 감소시킵니다.
            """)
    public ApplicationResponse<Void> decreasedCharacter(@RequestParam Long character_id, @RequestParam Integer activityPoint) {
        characterService.decreasedPoint(character_id, activityPoint);
        return ApplicationResponse.ok(null);
    }
}
