package com.vincenzoracca.boot4.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record Book(
        @Id Long id,
        BookType bookType,
        String isbn,
        String title, String author,
        BigDecimal price,
        LocalDate publicationDate,
        String format,
        BigDecimal fileSizeMb,
        String narrator,
        Integer durationMinutes,
        @CreatedDate Instant createdAt,
        @LastModifiedDate Instant updatedAt) {

    public static BookBuilder builder() {
        return new BookBuilder();
    }


    public static class BookBuilder {
        private Long id;
        private BookType bookType;
        private String isbn;
        private String title;
        private String author;
        private BigDecimal price;
        private LocalDate publicationDate;
        private String format;
        private BigDecimal fileSizeMb;
        private String narrator;
        private Integer durationMinutes;
        private Instant createdAt;
        private Instant updatedAt;

        public BookBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookBuilder bookType(BookType bookType) {
            this.bookType = bookType;
            return this;
        }

        public BookBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public BookBuilder publicationDate(LocalDate publishedDate) {
            this.publicationDate = publishedDate;
            return this;
        }

        public BookBuilder format(String format) {
            this.format = format;
            return this;
        }

        public BookBuilder fileSizeMb(BigDecimal fileSizeMb) {
            this.fileSizeMb = fileSizeMb;
            return this;
        }

        public BookBuilder narrator(String narrator) {
            this.narrator = narrator;
            return this;
        }

        public BookBuilder durationMinutes(Integer durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        public BookBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public BookBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Book build() {
            return new Book(id, bookType, isbn, title, author, price, publicationDate, format, fileSizeMb, narrator, durationMinutes, createdAt, updatedAt);
        }
    }
}
