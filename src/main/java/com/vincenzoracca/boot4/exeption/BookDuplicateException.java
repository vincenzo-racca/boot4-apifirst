package com.vincenzoracca.boot4.exeption;

public class BookDuplicateException extends RuntimeException {

    private static final String FORMAT_MESSAGE = "Book with %s already exists";

    public BookDuplicateException(String isbn) {
        super(String.format(FORMAT_MESSAGE, isbn));
    }
}
