package com.dionathan.portfolio_api.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        String method,
        LocalDateTime timestamp,
        Map<String, String> fieldErrors
) {
}
