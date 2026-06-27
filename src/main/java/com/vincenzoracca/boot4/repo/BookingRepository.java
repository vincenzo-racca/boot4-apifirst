package com.vincenzoracca.boot4.repo;

import com.vincenzoracca.boot4.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface BookingRepository extends CrudRepository<Book, Long>,
        PagingAndSortingRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);
    
}
