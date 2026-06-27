package com.vincenzoracca.boot4.exeption;

import java.util.List;

public class InvalidSortException extends RuntimeException {

    private static final String FORMAT_MESSAGE = "Sort parameters %s not valid";

    public InvalidSortException(List<String> sort) {
        super(String.format(FORMAT_MESSAGE, sort));
    }
}
