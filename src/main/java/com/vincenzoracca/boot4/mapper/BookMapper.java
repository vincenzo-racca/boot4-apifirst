package com.vincenzoracca.boot4.mapper;

import com.vincenzoracca.boot4.model.Book;
import com.vincenzoracca.boot4.model.generated.*;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.awt.print.Paper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toBook(CreateBookRequest createBookRequest);

    PaperbackDto toPaperbackDto(Book book);

    EBookDto toEBookDto(Book book);

    AudioBookDto toAudioBookDto(Book book);

    BookPage toBookPage(Page<Book> page);

    default BookResponse toBookResponse(Book book) {
        return switch (book.bookType()) {
            case PAPERBACK -> toPaperbackDto(book);
            case EBOOK -> toEBookDto(book);
            case AUDIOBOOK -> toAudioBookDto(book);
        };
    }
}
