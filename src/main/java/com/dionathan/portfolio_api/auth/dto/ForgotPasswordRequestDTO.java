package com.dionathan.portfolio_api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequestDTO(

        @NotBlank(message = "Email obrigatório")
        @Email(message = "Email inválido")
        String email
) {
}
