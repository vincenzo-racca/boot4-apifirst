package com.vincenzoracca.boot4.mapper;

import com.vincenzoracca.boot4.model.Book;
import com.vincenzoracca.boot4.model.BookType;
import com.vincenzoracca.boot4.model.generated.AudioBookDto;
import com.vincenzoracca.boot4.model.generated.BookResponse;
import com.vincenzoracca.boot4.model.generated.CreateBookRequest;
import com.vincenzoracca.boot4.model.generated.EBookDto;
import com.vincenzoracca.boot4.model.generated.PaperbackDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTests {

    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @Test
    void dispatchesCreateRequestBySubtype() {
        CreateBookRequest request = new PaperbackDto()
                .pages(464)
                .weightGrams(new BigDecimal("730.0"))
                .bookType("PAPERBACK")
                .isbn("9780132350884")
                .title("Clean Code")
                .author("Robert C. Martin");

        Book book = bookMapper.toBook(request);

        assertThat(book.bookType()).isEqualTo(BookType.PAPERBACK);
        assertThat(book.pages()).isEqualTo(464);
        assertThat(book.weightGrams()).isEqualByComparingTo("730.0");
        assertThat(book.format()).isNull();
        assertThat(book.narrator()).isNull();
    }

    @Test
    void mapsDomainToConcreteResponseSubtype() {
        Book ebook = Book.builder()
                .bookType(BookType.EBOOK)
                .isbn("9781234567890")
                .title("Mastering Java 21")
                .author("Luca Rossi")
                .format("EPUB")
                .fileSizeMb(new BigDecimal("8.2"))
                .build();

        BookResponse response = bookMapper.toBookResponse(ebook);

        assertThat(response).isInstanceOf(EBookDto.class);
        EBookDto dto = (EBookDto) response;
        assertThat(dto.getBookType()).isEqualTo("EBOOK");
        assertThat(dto.getFormat()).isEqualTo("EPUB");
        assertThat(dto.getFileSizeMb()).isEqualByComparingTo("8.2");

        Book audio = Book.builder()
                .bookType(BookType.AUDIOBOOK)
                .isbn("9782345678901")
                .title("Cloud Native Architecture")
                .author("Marco Bianchi")
                .narrator("Luca Ward")
                .durationMinutes(450)
                .build();

        assertThat(bookMapper.toBookResponse(audio)).isInstanceOf(AudioBookDto.class);
    }
}
