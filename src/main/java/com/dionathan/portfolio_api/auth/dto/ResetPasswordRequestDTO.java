package com.dionathan.portfolio_api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequestDTO(

        @NotBlank(message = "Senha obrigatória")
        @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
        @Pattern(regexp = ".*[A-Za-z].*")
        String newPassword

) {
}
