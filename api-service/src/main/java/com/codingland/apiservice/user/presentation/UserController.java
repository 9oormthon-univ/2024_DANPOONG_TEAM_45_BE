package com.codingland.apiservice.user.presentation;

import com.codingland.apiservice.user.service.LoginUseCase;
import com.codingland.common.common.ApplicationResponse;
import com.codingland.domain.user.dto.request.EditUserRequest;
import com.codingland.domain.user.dto.response.FindAllUserResponse;
import com.codingland.domain.user.dto.response.LoginAddResponse;
import com.codingland.domain.user.dto.response.UserResponse;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.service.UserService;
import com.codingland.security.annotation.UserResolver;
import com.codingland.security.oauth.dto.request.KakaoLoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@Validated
@RequiredArgsConstructor
@Tag(name = "[User] 유저 API", description = "유저 관련 정보 제공")
public class UserController {

    private final LoginUseCase loginUseCase;
    private final UserService userService;

    @PostMapping("/login/kakao")
    @Operation(summary = "유저 로그인", description = """
            (사용자용) 서비스에 로그인 할 때 사용됩니다.
            """)
    public ApplicationResponse<LoginAddResponse> kakaoLogin(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
        LoginAddResponse response = loginUseCase.kakaoLogin(kakaoLoginRequest);
        return ApplicationResponse.ok(response);
    }

    @GetMapping("/{user_id}")
    @Operation(summary = "유저 단 건 조회", description = """
            (사용자용) 유저를 단 건 조회할 때 사용됩니다.
            """)
    public ApplicationResponse<UserResponse> findByUserId(@PathVariable Long user_id) {
        UserResponse result = userService.findByUserId(user_id);
        return ApplicationResponse.ok(result);
    }

    @GetMapping("/all")
    @Operation(summary = "등록된 유저를 모두 조회", description = """
            (관리자용) 등록된 유저를 모두 보고 싶을때 사용합니다.
            """)
    public ApplicationResponse<FindAllUserResponse> findAllUser() {
        FindAllUserResponse result = userService.findAllUser();
        return ApplicationResponse.ok(result);
    }

    @PatchMapping
    @Operation(summary = "유저 수정", description = """
            (사용자용) 특정 User를 수정할 때 사용합니다.
            """)
    public ApplicationResponse<Void> editQuiz(@RequestBody EditUserRequest editUserRequest) {
        userService.editUser(editUserRequest);
        return ApplicationResponse.ok(null);
    }


    @GetMapping("/complete/training")
    @Operation(summary = "기초 트레이닝 완료 처리", description = """
            (사용자) 기초 트레이닝을 완료 처리합니다.
            """)
    public ApplicationResponse<Void> completeTraining(@UserResolver User user) {
        userService.completeTraining(user.getUserId());
        return ApplicationResponse.ok(null);
    }

    @GetMapping("/check/training")
    @Operation(summary = "기초 트레이닝 완료 여부 확인", description = """
            (사용자) 기초 트레이닝을 완료 여부를 확인합니다.
            """)
    public ApplicationResponse<Boolean> checkIsCompleteTraining(@UserResolver User user) {
        boolean result = userService.checkIsCompleteTraining(user.getUserId());
        return ApplicationResponse.ok(result);
    }


}
