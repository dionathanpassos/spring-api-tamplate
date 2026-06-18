package com.dionathan.portfolio_api.auth.dto;

public record TokenDTO(
        String accessToken,
        String refreshToken
) {
}
