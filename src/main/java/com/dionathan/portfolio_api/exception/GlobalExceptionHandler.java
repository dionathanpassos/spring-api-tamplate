package com.dionathan.portfolio_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> hendleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> hendleBusiness(
            BusinessException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;

        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> hendleConflict(
            ConflictException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.CONFLICT;

        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> hendleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InvalidLinkException.class)
    public ResponseEntity<ErrorResponse> hendleInvalidLink(
            InvalidLinkException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(status).body(error);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> hendleMissingRequestParam(
            MissingServletRequestParameterException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message;

        if ("token".equals(ex.getParameterName()) && request.getRequestURI().contains("/verify-email")) {
            message = "Link de verificação inválido";
        } else {
            message = String.format("O parâmetro '%s' é obrigatório.", ex.getParameterName());
        }

        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(status).body(error);
    }
}
