package com.alex.gamestore.exceptions;

public class InvalidProductCountException extends RuntimeException {
    public InvalidProductCountException(String message) {
        super(message);
    }
}
