package com.vincenzoracca.boot4.exeption;

public class BookNotFoundException extends RuntimeException {

    private static final String FORMAT_MESSAGE = "Book with %s not found";

    public BookNotFoundException(String isbn) {
        super(String.format(FORMAT_MESSAGE, isbn));
    }
}
