package com.alex.gamestore.exceptions;

public class NoProductFoundException extends RuntimeException {
    public NoProductFoundException(String message) {
        super(message);
    }
}
