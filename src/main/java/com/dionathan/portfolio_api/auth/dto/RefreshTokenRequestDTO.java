package com.dionathan.portfolio_api.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDTO(

        @NotBlank
        String refreshToken
) {
}
