package com.vincenzoracca.boot4.model;

public enum BookType {

    PAPERBACK("PAPERBACK"),
    EBOOK("EBOOK"),
    AUDIOBOOK("AUDIO");

    private String value;

    BookType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BookType{" +
                "value='" + value + '\'' +
                '}';
    }
}
