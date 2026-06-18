package com.dionathan.portfolio_api.auth;

import com.dionathan.portfolio_api.auth.dto.*;
import com.dionathan.portfolio_api.security.JwtService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody RegisterRequestDTO requestDTO) {
        UserResponseDTO created = authService.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        TokenDTO token = authService.login(requestDTO);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@Valid @RequestBody RefreshTokenRequestDTO requestDTO ) {
        TokenDTO token = authService.refreshToken(requestDTO);

        return ResponseEntity.ok(token);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestParam("token") String token) {
        authService.verifyEmail(token);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<MessageResponse> resendVerification(@Valid @RequestBody ResendVerificationRequestDTO resendVerificationRequestDTO) {
        authService.resendVerification(resendVerificationRequestDTO);

        return ResponseEntity.ok(
                new MessageResponse("Se existir uma conta associada a este email, um novo link de verificação foi enviado.")
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPasswrod(@Valid @RequestBody ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        authService.forgotPassword(forgotPasswordRequestDTO);

        return ResponseEntity.ok(
                new MessageResponse("Se existir uma conta associada a este email, um novo link de verificação foi enviado.")
        );
    }
}
