package com.dionathan.portfolio_api.infra.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationEmailService {

    private final EmailService emailService;

    public void sendVerificationEmail(String email, String token) {
        String verificationLink ="http://localhost:8080/api/v1/auth/verify-email?token=" + token;
        EmailMessage message = new EmailMessage(
                email,
                "Verificação de email",
                """
                        Olá!
                        
                        Obrigado por se cadastrar.
                        
                        Clique no link abaixo para confirmar o seu e-mail:
                        
                        %s
                        
                        Caso não tenha realizado este cadastro, ignore esta mensagem.
                        
                        """.formatted(verificationLink)
        );

        emailService.send(message);
    }
}
