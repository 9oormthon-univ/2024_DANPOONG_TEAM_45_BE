package com.codingland.apiservice.home.presentation;


import com.codingland.common.common.ApplicationResponse;
import com.codingland.domain.home.dto.RequestEditHomeDto;
import com.codingland.domain.home.dto.ResponseHomeDto;
import com.codingland.domain.home.dto.ResponseHomeListDto;
import com.codingland.domain.home.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/home")
@RequiredArgsConstructor
@Tag(name = "[Home] 홈 API", description = "홈 생성, 홈 조회, 홈 수정, 홈 삭제")
public class HomeController {
    private final HomeService homeService;

    @PostMapping
    @Operation(summary = "홈 등록", description = """
            홈을 등록합니다.
            """)
    public ApplicationResponse<Void> createHome() {
        homeService.createHome();
        return ApplicationResponse.ok(null);
    }

    @GetMapping("/{home_id}")
    @Operation(summary = "홈 단 건 조회", description = """
            홈을 단 건 조회합니다.
            """)
    public ApplicationResponse<ResponseHomeDto> getHome(@PathVariable Long home_id) {
        ResponseHomeDto result = homeService.getHome(home_id);
        return ApplicationResponse.ok(result);
    }

    @GetMapping("/all")
    @Operation(summary = "등록된 홈 모두 조회", description = """
            데이터베이스에 등록된 모든 홈을 조회합니다.
            """)
    public ApplicationResponse<ResponseHomeListDto> getAllHomes() {
        ResponseHomeListDto result = homeService.getHomeList();
        return ApplicationResponse.ok(result);
    }

    @PatchMapping("/{home_id}")
    @Operation(summary = "홈 수정", description = """
            홈 정보를 수정합니다.
            """)
    public ApplicationResponse<Void> editHome(@PathVariable Long home_id, @RequestBody RequestEditHomeDto requestEditHomeDto) {
        homeService.editHome(home_id, requestEditHomeDto);
        return ApplicationResponse.ok(null);
    }

    @DeleteMapping("/{home_id}")
    @Operation(summary = "홈 삭제", description = """
            홈을 삭제합니다.
            """)
    public ApplicationResponse<Void> deleteHome(@PathVariable Long home_id) {
        homeService.deleteHome(home_id);
        return ApplicationResponse.ok(null);
    }
}










