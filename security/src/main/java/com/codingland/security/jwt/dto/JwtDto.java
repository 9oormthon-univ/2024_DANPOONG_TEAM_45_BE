package com.codingland.security.jwt.dto;

public record JwtDto(
	String accessToken,
	String refreshToken
) {
}
