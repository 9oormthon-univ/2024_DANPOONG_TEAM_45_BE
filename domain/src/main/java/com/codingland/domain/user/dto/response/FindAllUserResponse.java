package com.codingland.domain.user.dto.response;

import java.util.List;

public record FindAllUserResponse(
        List<UserResponse> result
) {
}
