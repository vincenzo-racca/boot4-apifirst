package com.vincenzoracca.boot4.exeption;

public class InvalidBookRequestException extends RuntimeException {

    public InvalidBookRequestException(String message) {
        super(message);
    }
}
