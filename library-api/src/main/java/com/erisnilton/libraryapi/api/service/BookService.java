package com.erisnilton.libraryapi.api.service;

import com.erisnilton.libraryapi.api.model.Book;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    Book save(Book book);
}
