package com.codingland.apiservice.user.presentation;

import com.codingland.apiservice.user.service.LoginUseCase;
import com.codingland.common.common.ApplicationResponse;
import com.codingland.domain.user.dto.response.LoginAddResponse;
import com.codingland.security.oauth.dto.request.KakaoLoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@Validated
@RequiredArgsConstructor
@Tag(name = "[User] 유저 API", description = "유저 관련 정보 제공")
public class UserController {

    private final LoginUseCase loginUseCase;

    @PostMapping("/login/kakao")
    @Operation(summary = "퀴즈 단 건 조회", description = """
            (사용자용) 퀴즈를 단 건 조회할 때 사용 됩니다.
            """)
    public ApplicationResponse<LoginAddResponse> kakaoLogin(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
        LoginAddResponse response = loginUseCase.kakaoLogin(kakaoLoginRequest);
        return ApplicationResponse.ok(response);
    }
}
