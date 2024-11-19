package com.codingland.security.oauth.dto.response;

import java.util.HashMap;
import java.util.Map;

public record OidcDecodePayload(
        String iss,
        String aud,
        String sub,
        String nickname,
        String picture,
        String email
) {

    public Map<String, Object> getClaims() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", iss);
        claims.put("aud", aud);
        claims.put("sub", sub);
        claims.put("nickname", nickname);
        claims.put("picture", picture);
        claims.put("email", email);
        return claims;
    }
}
