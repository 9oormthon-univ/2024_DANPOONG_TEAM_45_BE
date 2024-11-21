package com.codingland.domain.user.dto.response;

import lombok.Builder;

@Builder
public record UserResponse(
        Long userId,
        String subId,
        String name,
        String picture,
        String email
) {
}
