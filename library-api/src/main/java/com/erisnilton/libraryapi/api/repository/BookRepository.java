package com.erisnilton.libraryapi.api.repository;

import com.erisnilton.libraryapi.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByISBN(String isbn);
}
