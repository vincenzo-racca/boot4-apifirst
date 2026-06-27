package com.vincenzoracca.boot4.service;

import com.vincenzoracca.boot4.exeption.BookDuplicateException;
import com.vincenzoracca.boot4.exeption.BookNotFoundException;
import com.vincenzoracca.boot4.mapper.BookMapper;
import com.vincenzoracca.boot4.model.Book;
import com.vincenzoracca.boot4.model.generated.BookPage;
import com.vincenzoracca.boot4.model.generated.BookResponse;
import com.vincenzoracca.boot4.model.generated.CreateBookRequest;
import com.vincenzoracca.boot4.repo.BookingRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookingRepository bookingRepository;
    private final BookMapper bookMapper;

    public BookService(BookingRepository bookingRepository, BookMapper bookMapper) {
        this.bookingRepository = bookingRepository;
        this.bookMapper = bookMapper;
    }

    public BookResponse findByIsbn(String isbn) {
        return bookingRepository.findByIsbn(isbn)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public BookPage findAll(Integer pageNumber, Integer size) {
        Page<Book> page = bookingRepository.findAll(PageRequest.of(pageNumber, size));
        return bookMapper.toBookPage(page);
    }

    @Transactional
    public Book save(CreateBookRequest request) {
        Book book = bookMapper.toBook(request);

        try {
            return bookingRepository.save(book);
        }
        catch (DuplicateKeyException e) {
            throw new BookDuplicateException(request.getIsbn());
        }

    }
}
