package com.vincenzoracca.boot4.mapper;

import com.vincenzoracca.boot4.model.Book;
import com.vincenzoracca.boot4.model.generated.BookPage;
import com.vincenzoracca.boot4.model.generated.BookResponse;
import com.vincenzoracca.boot4.model.generated.CreateBookRequest;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toBook(CreateBookRequest request);

    BookResponse toBookResponse(Book book);

    BookPage toBookPage(Page<Book> page);
}
