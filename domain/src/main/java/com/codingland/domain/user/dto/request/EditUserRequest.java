package com.codingland.domain.user.dto.request;

public record EditUserRequest(
        Long userId,
        String subId,
        String name,
        String picture,
        String email
) {
}
