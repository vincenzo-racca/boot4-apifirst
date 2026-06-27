package com.vincenzoracca.boot4.api;

import com.vincenzoracca.boot4.api.generated.BooksApi;
import com.vincenzoracca.boot4.model.Book;
import com.vincenzoracca.boot4.model.generated.BookPage;
import com.vincenzoracca.boot4.model.generated.BookResponse;
import com.vincenzoracca.boot4.model.generated.CreateBookRequest;
import com.vincenzoracca.boot4.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class BookingController implements BooksApi {

    private final BookService bookService;

    public BookingController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public ResponseEntity<Void> createBook(CreateBookRequest request) {
        Book saved = bookService.save(request);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{isbn}")
                .buildAndExpand(saved.isbn())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<BookResponse> getBookByIsbn(String isbn) {
        BookResponse response = bookService.findByIsbn(isbn);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BookPage> getAllBooks(
            Integer page,
            Integer size,
            List<String> sort
    ) {
        BookPage bookPage = bookService.findAll(page, size);
        return ResponseEntity.ok(bookPage);
    }
}
