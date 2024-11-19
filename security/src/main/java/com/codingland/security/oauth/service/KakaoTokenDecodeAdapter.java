package com.codingland.security.oauth.service;

import com.codingland.common.exception.security.SecurityCustomException;
import com.codingland.common.exception.security.SecurityErrorCode;
import com.codingland.security.oauth.dto.response.OidcDecodePayload;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class KakaoTokenDecodeAdapter {
    private final String iss;
    private final String clientId;

    public KakaoTokenDecodeAdapter(
            @Value("${oauth.kakao.iss}") String iss,
            @Value("${oauth.kakao.rest-api-id}") String clientId
    ) {
        this.iss = iss;
        this.clientId = clientId;
    }

    @Transactional(propagation = Propagation.MANDATORY) // Redis에 접근하므로 Tranasactional
    public OidcDecodePayload getPayloadFromAccessToken(final String token) {

        // Kakao API를 사용하여 Access Token으로 사용자 정보 가져오기
        String userInfoEndpointUri = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token); // 변경된 부분
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new SecurityCustomException(SecurityErrorCode.KAKAO_KEY_SERVER_ERROR);
        }

        String responseBody = response.getBody();
        // 사용자 정보 파싱
        return parseUserInfo(responseBody);
    }

    private OidcDecodePayload parseUserInfo(String userInfoJson) {
        JsonObject jsonObject = JsonParser.parseString(userInfoJson).getAsJsonObject();

        String sub = jsonObject.has("id") ? jsonObject.get("id").getAsString() : null;
        String email = jsonObject.has("kakao_account") && jsonObject.getAsJsonObject("kakao_account").has("email")
                ? jsonObject.getAsJsonObject("kakao_account").get("email").getAsString() : null;
        String nickname = jsonObject.has("properties") && jsonObject.getAsJsonObject("properties").has("nickname")
                ? jsonObject.getAsJsonObject("properties").get("nickname").getAsString() : null;
        String picture = jsonObject.has("properties") && jsonObject.getAsJsonObject("properties").has("profile_image")
                ? jsonObject.getAsJsonObject("properties").get("profile_image").getAsString() : null;

        return new OidcDecodePayload(
                iss,
                clientId,
                sub,
                nickname,
                picture,
                email
        );
    }

}
