package com.codingland.apiservice.dailyQuestProgress.presentation;

import com.codingland.common.common.ApplicationResponse;
import com.codingland.domain.dailyQuestProgress.dto.ResponseDailyQuestProgressDto;
import com.codingland.domain.dailyQuestProgress.service.DailyQuestProgressService;
import com.codingland.domain.user.entity.User;
import com.codingland.security.annotation.UserResolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/dailyQuestProgress")
@RequiredArgsConstructor
@Tag(name = "[Daily Quest Progress] 하루 퀘스트 진행도", description = "퀘스트 진행도 조회")
public class DailyQuestProgressController {
    private final DailyQuestProgressService dailyQuestProgressService;

    @GetMapping
    @Operation(summary = "일일 퀘스트 조회", description = """
            사용자의 일일 퀘스트를 조회합니다.
            """)
    public ApplicationResponse<ResponseDailyQuestProgressDto> getDailyQuestProgress(@UserResolver User user) {
        ResponseDailyQuestProgressDto result = dailyQuestProgressService.getDailyQuestProgress(user.getUserId());
        return ApplicationResponse.ok(result);
    }

    @PostMapping
    @Operation(summary = "일일 퀘스트 완료처리", description = """
            사용자의 일일퀘스트를 완료처리 합니다.
            """)
    public ApplicationResponse<Void> markDailyQuestProgress(@UserResolver User user) {
        dailyQuestProgressService.markQuestCompleted(user.getUserId());
        return ApplicationResponse.ok(null);
    }
}
