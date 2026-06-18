package com.dionathan.portfolio_api.auth.dto;

import com.dionathan.portfolio_api.auth.Role;
import jakarta.validation.constraints.NotBlank;

public record UserResponseDTO(

        Long id,
        String name,
        String email,
        Role role
) {
}
