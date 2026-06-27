package com.vincenzoracca.boot4.service;

import com.vincenzoracca.boot4.exeption.InvalidBookRequestException;
import com.vincenzoracca.boot4.mapper.BookMapper;
import com.vincenzoracca.boot4.model.generated.CreateBookRequest;
import com.vincenzoracca.boot4.repo.BookingRepository;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

class BookServiceTests {

    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);
    private final BookService bookService = new BookService(bookingRepository, bookMapper);

    @Test
    void rejectsSubtypeFieldsThatDoNotMatchBookType() {
        CreateBookRequest request = new CreateBookRequest()
                .bookType(CreateBookRequest.BookTypeEnum.EBOOK)
                .isbn("9781234567890")
                .title("Mastering Java 21")
                .author("Luca Rossi")
                .pages(300);

        assertThatThrownBy(() -> bookService.save(request))
                .isInstanceOf(InvalidBookRequestException.class)
                .hasMessage("EBOOK books cannot contain pages, weightGrams, narrator or durationMinutes");

        verifyNoInteractions(bookingRepository);
    }
}
