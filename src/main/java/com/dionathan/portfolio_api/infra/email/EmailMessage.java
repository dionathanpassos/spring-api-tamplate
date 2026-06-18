package com.dionathan.portfolio_api.infra.email;

public record EmailMessage(
        String to,
        String subject,
        String body
) {
}
