package com.vincenzoracca.boot4.service;

import com.vincenzoracca.boot4.exeption.BookDuplicateException;
import com.vincenzoracca.boot4.exeption.BookNotFoundException;
import com.vincenzoracca.boot4.exeption.InvalidSortException;
import com.vincenzoracca.boot4.mapper.BookMapper;
import com.vincenzoracca.boot4.model.Book;
import com.vincenzoracca.boot4.model.generated.BookPage;
import com.vincenzoracca.boot4.model.generated.BookResponse;
import com.vincenzoracca.boot4.model.generated.CreateBookRequest;
import com.vincenzoracca.boot4.repo.BookingRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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

    public BookPage findAll(Integer pageNumber, Integer size, List<String> sort) {
        try {
            Page<Book> page = bookingRepository.findAll(PageRequest.of(pageNumber, size, parseSort(sort)));
            return bookMapper.toBookPage(page);
        }
        catch (BadSqlGrammarException e) {
            if(CollectionUtils.isEmpty(sort)) {
                throw e;
            }
            throw new InvalidSortException(sort);

        }
    }

    @Transactional
    public Book save(CreateBookRequest request) {
        Book book = bookMapper.toBook(request);

        try {
            return bookingRepository.save(book);
        }
        catch (DuplicateKeyException e) {
            throw new BookDuplicateException(book.isbn());
        }

    }

    /**
     * Utility method to map the OpenAPI List<String> to a Sort object
     */
    private Sort parseSort(List<String> sortList) {
        if (sortList == null || sortList.isEmpty()) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = new ArrayList<>();

        for (int i = 0; i < sortList.size(); i++) {
            String currentItem = sortList.get(i).trim();

            // CASO 1: L'elemento contiene già la virgola (es. "title,asc")
            if (currentItem.contains(",")) {
                String[] parts = currentItem.split(",");
                String property = parts[0].trim();
                Sort.Direction direction = Sort.Direction.ASC; // Default

                if (parts.length > 1) {
                    String dirString = parts[parts.length - 1].trim();
                    if (dirString.equalsIgnoreCase("desc")) {
                        direction = Sort.Direction.DESC;
                    }
                }
                orders.add(new Sort.Order(direction, property));
            }
            // CASO 2: Spring ha separato i valori per via della virgola (es. ["title", "asc", "author"])
            else {
                String property = currentItem;
                Sort.Direction direction = Sort.Direction.ASC; // Default

                // Guardiamo l'elemento successivo nella lista per discriminare se è una direction
                if (i + 1 < sortList.size()) {
                    String nextItem = sortList.get(i + 1).trim();
                    if (nextItem.equalsIgnoreCase("asc") || nextItem.equalsIgnoreCase("desc")) {
                        // È una direction, la applichiamo e avanziamo l'indice
                        direction = nextItem.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                        i++; // Saltiamo l'elemento successivo nel ciclo for perché lo abbiamo consumato
                    }
                }
                // Se l'elemento successivo non era asc/desc, viene trattato come un nuovo campo al giro successivo
                orders.add(new Sort.Order(direction, property));
            }
        }

        return Sort.by(orders);
    }
}
