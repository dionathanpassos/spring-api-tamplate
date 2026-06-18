package com.dionathan.portfolio_api.auth;

import com.dionathan.portfolio_api.auth.dto.RegisterRequestDTO;
import com.dionathan.portfolio_api.auth.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegisterRequestDTO requestDTO, String encodedPassword, Role role){
        User user = new User();

        user.setName(requestDTO.name());
        user.setEmail(requestDTO.email());
        user.setPassword(encodedPassword);
        user.setRole(role);

        return user;
    }

    public UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
