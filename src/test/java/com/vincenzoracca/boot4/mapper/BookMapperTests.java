package com.vincenzoracca.boot4.mapper;

import com.vincenzoracca.boot4.model.Book;
import com.vincenzoracca.boot4.model.BookType;
import com.vincenzoracca.boot4.model.generated.BookResponse;
import com.vincenzoracca.boot4.model.generated.CreateBookRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTests {

    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @Test
    void mapsFlatCreateRequestToDomainAndDomainToBookResponse() {
        CreateBookRequest request = new CreateBookRequest()
                .bookType(CreateBookRequest.BookTypeEnum.PAPERBACK)
                .isbn("9780132350884")
                .title("Clean Code")
                .author("Robert C. Martin")
                .price(new BigDecimal("45.00"))
                .publicationDate(LocalDate.parse("2008-08-01"))
                .pages(464)
                .weightGrams(new BigDecimal("730.0"));

        Book book = bookMapper.toBook(request);
        BookResponse response = bookMapper.toBookResponse(book);

        assertThat(book.bookType()).isEqualTo(BookType.PAPERBACK);
        assertThat(book.pages()).isEqualTo(464);
        assertThat(book.weightGrams()).isEqualByComparingTo("730.0");
        assertThat(response.getBookType()).isEqualTo(BookResponse.BookTypeEnum.PAPERBACK);
        assertThat(response.getPublicationDate()).isEqualTo(LocalDate.parse("2008-08-01"));
        assertThat(response.getPages()).isEqualTo(464);
        assertThat(response.getWeightGrams()).isEqualByComparingTo("730.0");
    }
}
