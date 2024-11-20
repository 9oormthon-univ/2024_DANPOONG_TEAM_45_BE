package com.codingland.apiservice.user.service;

import com.codingland.domain.user.dto.response.LoginAddResponse;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.repository.UserRepository;
import com.codingland.domain.user.service.UserQueryService;
import com.codingland.security.jwt.JwtUtil;
import com.codingland.security.oauth.dto.request.KakaoLoginRequest;
import com.codingland.security.oauth.dto.response.LoginInfo;
import com.codingland.security.oauth.dto.response.LoginResponse;
import com.codingland.security.oauth.dto.response.OidcDecodePayload;
import com.codingland.security.oauth.service.KakaoTokenDecodeAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final KakaoTokenDecodeAdapter kakaoTokenDecodeAdapter;
    private final UserQueryService userQueryService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public LoginAddResponse kakaoLogin(final KakaoLoginRequest kakaoLoginRequest) {

        // ID 토큰으로 찾아온 유저 정보
        final OidcDecodePayload oidcDecodePayload = kakaoTokenDecodeAdapter.getPayloadFromAccessToken(kakaoLoginRequest.idToken());

        // User 존재 여부 확인 및 조회
        final Optional<User> optionalUser = userRepository.findBySubId(oidcDecodePayload.sub());
        final boolean existYn = optionalUser.isPresent();

        // 유저가 존재하지 않으면 새로운 유저 생성
        final User user = optionalUser.orElseGet(() -> createNewKakaoUser(oidcDecodePayload));

        // JWT 토큰 생성
        final String accessToken = jwtUtil.createJwtAccessToken(oidcDecodePayload.email(), oidcDecodePayload.sub());
        final String refreshToken = jwtUtil.createJwtRefreshToken(oidcDecodePayload.email(), oidcDecodePayload.sub());

        // 응답 객체 생성 및 반환
        return new LoginAddResponse(user.getName(), user.getPicture(), existYn, accessToken, refreshToken);
    }


    private User createNewKakaoUser(final OidcDecodePayload oidcDecodePayload) {

        final User newUser = User.createSocialUser(oidcDecodePayload.sub(), oidcDecodePayload.nickname(), oidcDecodePayload.picture(), oidcDecodePayload.email());
        return userRepository.save(newUser);
    }

    public LoginInfo getLoginInfo(String accessToken) {

        String email = jwtUtil.getEmail(accessToken);
        User user = userQueryService.findByEmail(email);

        return new LoginInfo(
                user.getName(),
                user.getPicture(),
                user.getEmail()
        );
    }

    @Transactional
    public LoginResponse reissueToken(String refreshToken) {

        return jwtUtil.reissueToken(refreshToken);
    }

    @Transactional
    public void logout(String refreshToken, String name) {

        jwtUtil.deleteToken(refreshToken);
        if (userRepository.findByName(name).isPresent()) userRepository.deleteByName(name);
    }
}
