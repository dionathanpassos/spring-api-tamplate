package com.dionathan.portfolio_api.auth;

import com.dionathan.portfolio_api.auth.dto.*;
import com.dionathan.portfolio_api.exception.BusinessException;
import com.dionathan.portfolio_api.exception.InvalidLinkException;
import com.dionathan.portfolio_api.exception.ResourceNotFoundException;
import com.dionathan.portfolio_api.infra.email.ResetPasswordEmailService;
import com.dionathan.portfolio_api.infra.email.VerificationEmailService;
import com.dionathan.portfolio_api.security.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VerificationEmailService verificationEmailService;
    private final ResetPasswordEmailService resetPasswordEmailService;

    @Transactional
    public UserResponseDTO create(RegisterRequestDTO requestDTO) {

        if(userRepository.existsByEmail(requestDTO.email())) {
            throw new BusinessException("Email em utilização");
        }

        String encodedPassword = passwordEncoder.encode(requestDTO.password());

        User user = userMapper.toEntity(requestDTO, encodedPassword, Role.ROLE_USER);
        User saved = userRepository.save(user);

        String validationToken = jwtService.generateValidationToken(user);
        verificationEmailService.sendVerificationEmail(user.getEmail(), validationToken);

        return userMapper.fromEntity(saved);
    }

    public TokenDTO login(LoginRequestDTO requestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.email(),
                        requestDTO.password()
                )
        );
        User user = (User) authentication.getPrincipal();

        if (!user.isEmailVerified()) {
            throw new BusinessException("Email não verificado. Acesse sua caixa de entrada e verifique acessando o link enviado.");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new TokenDTO(accessToken, refreshToken);
    }

    @Transactional
    public void verifyEmail(String token) {
        try{
            Long userId = jwtService.validateValidationToken(token);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

            if(user.isEmailVerified()){
                throw new BusinessException("Email já validado");
            }
            user.setEmailVerified(true);
        }
        catch (ExpiredJwtException e) {
            throw new InvalidLinkException("Link de validação expirado");
        }
        catch (JwtException e) {
            throw new InvalidLinkException("Link de validação inválido");
        }
    }

    public void resendVerification(ResendVerificationRequestDTO resendVerificationRequestDTO) {

        userRepository.findByEmail(resendVerificationRequestDTO.email())
                .filter(user -> !user.isEmailVerified())
                .ifPresent(user -> {
                    String validationToken = jwtService.generateValidationToken(user);
                    verificationEmailService.sendVerificationEmail(user.getEmail(), validationToken);
                });

    }
    public void forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO) {

        userRepository.findByEmail(forgotPasswordRequestDTO.email())
                .ifPresent(user -> {
                    String validationToken = jwtService.generateResetPasswordToken(user);
                    resetPasswordEmailService.sendResetPasswordEmail(user.getEmail(), validationToken);
                });
    }

    public TokenDTO refreshToken(RefreshTokenRequestDTO requestDTO) {
        String refreshToken = requestDTO.refreshToken();

        Long userId = jwtService.validateRefreshToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        String newAccessToken = jwtService.generateAccessToken(user);

        return new TokenDTO(newAccessToken, refreshToken);
    }


}
