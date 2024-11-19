package com.codingland.security.jwt.dto;

public record UserInfoDTO(
        String name,
        String email,
        String image
) {
}