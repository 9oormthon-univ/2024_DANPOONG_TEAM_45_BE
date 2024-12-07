package com.codingland.apiservice.chapter.presentation;

import com.codingland.common.common.ApplicationResponse;
import com.codingland.domain.chapter.service.HasReceivedRewardService;
import com.codingland.domain.user.entity.User;
import com.codingland.security.annotation.UserResolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/chapter-reward")
@RequiredArgsConstructor
@Tag(name = "[HasReceivedReward] 챕터 보상 수령 여부 API", description = "챕터 보상 완료 처리")
public class HasReceivedRewardController {
    private final HasReceivedRewardService hasReceivedRewardService;

    @PostMapping
    @Operation(summary = "챕터 보상 완료 처리", description = """
            (사용자) 챕터 보상을 받았다고 처리합니다.
            """)
    public ApplicationResponse<Void> solvedChapter(
            @RequestParam Long chapterId,
            @UserResolver User user) {
        hasReceivedRewardService.processChapterReward(chapterId, user.getUserId());
        return ApplicationResponse.ok(null);
    }

}
