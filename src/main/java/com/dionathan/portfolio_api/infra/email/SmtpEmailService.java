package com.dionathan.portfolio_api.infra.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmtpEmailService implements EmailService{

    private final JavaMailSender mailSender;


    @Override
    public void send(EmailMessage message) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom("devdionathanpassos@gmail.com");
        mail.setTo(message.to());
        mail.setSubject(message.subject());
        mail.setText(message.body());

        mailSender.send(mail);

    }
}


