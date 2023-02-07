package com.erisnilton.libraryapi.api.service;

import com.erisnilton.libraryapi.api.dto.BookDTO;
import com.erisnilton.libraryapi.api.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public interface BookService {
    Book save(Book book);

    Optional<Book> getById(Long id);

    void delete(Book book);

    Book update(Book book);

    Page<Book> find(Book book, Pageable pageRequest);
}
