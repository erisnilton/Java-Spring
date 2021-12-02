package com.erisnilton.libraryapi.service;

import com.erisnilton.libraryapi.api.model.Book;
import com.erisnilton.libraryapi.api.repository.BookRepository;
import com.erisnilton.libraryapi.api.service.BookService;
import com.erisnilton.libraryapi.api.service.impl.BookServiceImpl;
import com.erisnilton.libraryapi.exception.BussinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService bookService;
    @MockBean
    BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        this.bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("should to save a book")
    public void saveBookTest() {
        //given
        Book book = createValidBook();
        Book bookForReturn = Book.builder().id(001L).title("my book").author("author").ISBN("123456").build();
        when(bookRepository.existsByISBN(book.getISBN())).thenReturn(false);
        when(bookRepository.save(book)).thenReturn(bookForReturn);

        // when
        Book bookSave = bookService.save(book);

        //then
        assertThat(bookSave.getId()).isNotNull();
        assertThat(bookSave.getTitle()).isEqualTo("my book");
        assertThat(bookSave.getAuthor()).isEqualTo("author");
        assertThat(bookSave.getISBN()).isEqualTo("123456");

    }



    @Test
    @DisplayName("Should throw error try register book with duplicated isbn")
    public void shouldNotSaveBookWithDuplicatedIsbn() {
        // given
        Book book = createValidBook();
        when(bookRepository.existsByISBN(book.getISBN())).thenReturn(true);

        // when
        Throwable exception = Assertions.catchThrowable(() -> bookService.save(book));

        //then
        assertThat(exception)
                .isInstanceOf(BussinessException.class)
                .hasMessage("Isbn already registered.");

        verify(bookRepository, never()).save(book);

    }

    @Test
    @DisplayName("Should get book by id")
    public void getBookById() {
        // Given
        Long id = 1l;
        Book book = createValidBook();
        book.setId(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // When
        Optional<Book> foundBook = bookService.getById(id);

        // Then
        assertThat(foundBook.isPresent()).isTrue();
        assertThat(foundBook.get().getId()).isEqualTo(id);
        assertThat(foundBook.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(foundBook.get().getAuthor()).isEqualTo(book.getAuthor());
        assertThat(foundBook.get().getISBN()).isEqualTo(book.getISBN());

    }
    @Test
    @DisplayName("Should return empty when search book by id inexistent")
    public void getBookByIdInexistent() {
        Long id  = 1l;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Book> book = bookService.getById(id);

        // Then
        assertThat(book.isPresent()).isFalse();

    }

    @Test
    @DisplayName("Should delete a book")
    public void deleteBook() {
        Book book = Book.builder().id(1l).build();
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() ->  bookService.delete(book));
        Mockito.verify(bookRepository, Mockito.times(1)).delete(book);
    }

    @Test
    @DisplayName("Should Throw error try delete a book without id")
    public void deleteInexistentBook() {
        Book book = Book.builder().build();
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.delete(book));
        Mockito.verify(bookRepository, Mockito.never()).delete(book);
    }
    @Test
    @DisplayName("Should update a book")
    public void updateBook() {
        Long id = 1L;
        Book updatingBook = Book.builder().id(id).build();

        Book updateBook = createValidBook();
        updateBook.setId(id);

        Mockito.when(bookRepository.save(updatingBook)).thenReturn(updateBook);

        Book book = bookService.update(updatingBook);


        Assertions.assertThat(book.getId()).isEqualTo(updateBook.getId());
        Assertions.assertThat(book.getTitle()).isEqualTo(updateBook.getTitle());
        Assertions.assertThat(book.getAuthor()).isEqualTo(updateBook.getAuthor());
        Assertions.assertThat(book.getISBN()).isEqualTo(updateBook.getISBN());
    }

    @Test
    @DisplayName("Should Throw error try update a book without id")
    public void updateInexistentBook() {
        Book book =  new Book();
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.update(book));
        Mockito.verify(bookRepository, Mockito.never()).save(book);
    }


    private Book createValidBook() {
        return Book.builder().title("my book").author("author").ISBN("123456").build();
    }

}
