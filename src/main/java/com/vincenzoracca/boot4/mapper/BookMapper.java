package com.vincenzoracca.boot4.mapper;

import com.vincenzoracca.boot4.model.Book;
import com.vincenzoracca.boot4.model.generated.*;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toBook(PaperbackDto bookDTo);

    PaperbackDto toPaperbackDto(Book book);

    Book toBook(EBookDto eBookDto);

    EBookDto toEBookDto(Book book);

    Book toBook(AudioBookDto audioBookDto);

    AudioBookDto toAudioBookDto(Book book);

    BookPage toBookPage(Page<Book> page);

    default BookResponse toBookResponse(Book book) {
        return switch (book.bookType()) {
            case PAPERBACK -> toPaperbackDto(book);
            case EBOOK -> toEBookDto(book);
            case AUDIOBOOK -> toAudioBookDto(book);
        };
    }

    default Book toBook(CreateBookRequest createBookRequest) {
        if(createBookRequest instanceof PaperbackDto paperbackDto) {
            return toBook(paperbackDto);
        }
        if(createBookRequest instanceof EBookDto eBookDto) {
            return toBook(eBookDto);
        }
        if(createBookRequest instanceof AudioBookDto audioBookDto) {
            return toBook(audioBookDto);
        }

        throw new IllegalArgumentException(
                String.format("Unsupported CreateBookRequest subtype: %s", createBookRequest));
    }


}
