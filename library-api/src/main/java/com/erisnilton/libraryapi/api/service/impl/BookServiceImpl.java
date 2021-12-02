package com.erisnilton.libraryapi.api.service.impl;

import com.erisnilton.libraryapi.api.model.Book;
import com.erisnilton.libraryapi.api.repository.BookRepository;
import com.erisnilton.libraryapi.api.service.BookService;
import com.erisnilton.libraryapi.exception.BussinessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Optional<Book> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Book book) {
        if(book == null || book.getId() == null) {
            throw new IllegalArgumentException("Book id cant be null.");
        }
        repository.delete(book);
    }

    @Override
    public Book update(Book book) {
        if(book == null || book.getId() == null) {
            throw new IllegalArgumentException("Book id cant be null.");
        }
       return repository.save(book);
    }
}
