package com.dionathan.portfolio_api.infra.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordEmailService {

    private final EmailService emailService;

    public void sendResetPasswordEmail(String email, String token) {
        String verificationLink ="http://localhost:8080/api/v1/auth/reset-password?token=" + token;
        EmailMessage message = new EmailMessage(
                email,
                "Redefinição de senha",
                """
                        Olá!
                        
                        Você solicitou um link para redefinição de senha.
                        
                        Clique no link abaixo para prosseguir:
                        
                        %s
                        
                        Caso não tenha realizado esta solicitação, ignore esta mensagem.
                        
                        """.formatted(verificationLink)
        );

        emailService.send(message);
    }
}
