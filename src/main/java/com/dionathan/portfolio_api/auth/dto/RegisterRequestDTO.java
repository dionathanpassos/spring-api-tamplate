package com.dionathan.portfolio_api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        String name,

        @NotBlank(message = "Email obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha obrigatória")
        @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
        @Pattern(regexp = ".*[A-Za-z].*")
        String password
) {
}
