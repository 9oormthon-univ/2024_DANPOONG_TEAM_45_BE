package com.codingland.apiservice.character.presentation;

import com.codingland.common.common.ApplicationResponse;
import com.codingland.domain.character.dto.RequestCharacterDto;
import com.codingland.domain.character.dto.ResponseCharacterDto;
import com.codingland.domain.character.dto.ResponseCreateCharacterDto;
import com.codingland.domain.character.service.CharacterService;
import com.codingland.domain.user.entity.User;
import com.codingland.security.annotation.UserResolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/character")
@RequiredArgsConstructor
@Tag(name = "[Character] 캐릭터 API", description = "캐릭터 생성, 캐릭터 이름 수정, 활동 포인트 상승 하락")
public class CharacterController {
    private final CharacterService characterService;

    @PostMapping
    @Operation(summary = "캐릭터 등록 및 홈 등록", description = """
            (사용자) 캐릭터를 등록합니다. 생성된 캐릭터와 연관관계가 맺어진 홈이 같이 생성됩니다.
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

    @GetMapping("/pickup/random")
    @Operation(summary = "캐릭터 랜덤 뽑기 후 홈화면 선인장 교체", description = """
            (사용자) 캐릭터를 랜덤 뽑기 한 후 홈화면에 있는 선인장을 새롭게 뽑은 선인장으로 교체합니다.
            """)
    public ApplicationResponse<ResponseCharacterDto> getRandomCharacter(@UserResolver User user) {
        ResponseCharacterDto result = characterService.pickRandomCharacter(user.getUserId());
        return ApplicationResponse.ok(result);
    }
}
