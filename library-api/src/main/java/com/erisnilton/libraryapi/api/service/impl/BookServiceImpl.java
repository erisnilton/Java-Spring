package com.erisnilton.libraryapi.api.service.impl;

import com.erisnilton.libraryapi.api.model.Book;
import com.erisnilton.libraryapi.api.repository.BookRepository;
import com.erisnilton.libraryapi.api.service.BookService;
import com.erisnilton.libraryapi.exception.BussinessException;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        if(repository.existsByISBN(book.getISBN())) {
            throw new BussinessException("Isbn already registered.");
        }
        return repository.save(book);
    }
}
