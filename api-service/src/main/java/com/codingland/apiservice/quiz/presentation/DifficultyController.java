package com.codingland.apiservice.quiz.presentation;

import com.codingland.common.common.ApplicationResponse;
import com.codingland.domain.quiz.dto.ResponseDifficultyListDto;
import com.codingland.domain.quiz.service.DifficultyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/difficulty")
@RequiredArgsConstructor
@Tag(name = "[Difficulty] 난이도 API", description = "난이도 생성, 난이도 조회, 난이도 수정, 난이도 삭제")
public class DifficultyController {
    private final DifficultyService difficultyService;

    @PostMapping
    @Operation(summary = "난이도 등록", description = """
            (관리자용) 난이도를 등록할 때 사용합니다.
            """)
    public ApplicationResponse<Void> createDifficulty(@RequestParam int level) {
        difficultyService.createDifficulty(level);
        return ApplicationResponse.ok(null);
    }

    @GetMapping("/all")
    @Operation(summary = "등록된 난이도를 모두 조회", description = """
            (관리자용)데이터베이스에 등록된 난이도를 모두 조회 할 때 사용합니다.
            """)
    public ApplicationResponse<ResponseDifficultyListDto> getDifficultyList() {
        ResponseDifficultyListDto result = difficultyService.getDifficultyList();
        return ApplicationResponse.ok(result);
    }

    @PatchMapping
    @Operation(summary = "난이도 수정", description = """
            (관리자용) 난이도 수치를 조정할 때 사용합니다.
            """)
    public ApplicationResponse<Void> editDifficulty(
            @RequestParam Long difficulty_id,
            @RequestParam int level) {
        difficultyService.editDifficulty(difficulty_id, level);
        return ApplicationResponse.ok(null);
    }

    @DeleteMapping
    @Operation(summary = "난이도 삭제", description = """
            (관리자용) 난이도를 삭제할 때 사용합니다. 퀴즈와 관계가 맺어진 난이도라면 삭제 될 수 없습니다.
            """)
    public ApplicationResponse<Void> deleteDifficulty(@RequestParam Long difficulty_id) {
        difficultyService.deleteDifficulty(difficulty_id);
        return ApplicationResponse.ok(null);
    }


}
