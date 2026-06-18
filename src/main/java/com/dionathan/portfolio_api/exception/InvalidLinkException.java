package com.dionathan.portfolio_api.exception;

public class InvalidLinkException extends RuntimeException{
    public InvalidLinkException(String message) {
        super(message);
    }
}
