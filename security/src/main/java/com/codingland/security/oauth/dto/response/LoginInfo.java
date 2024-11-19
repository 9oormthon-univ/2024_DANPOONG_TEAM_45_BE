package com.codingland.security.oauth.dto.response;

public record LoginInfo(
        String name,
        String picture,
        String email
) {
}
