package com.erisnilton.libraryapi.servive;

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

import static org.assertj.core.api.Assertions.assertThat;

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
        Mockito.when(bookRepository.existsByISBN(book.getISBN())).thenReturn(false);
        Mockito.when(bookRepository.save(book)).thenReturn(bookForReturn);

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
        Mockito.when(bookRepository.existsByISBN(book.getISBN())).thenReturn(true);

        // when
        Throwable exception = Assertions.catchThrowable(() -> bookService.save(book));

        //then
        assertThat(exception)
                .isInstanceOf(BussinessException.class)
                .hasMessage("Isbn already registered.");

        Mockito.verify(bookRepository, Mockito.never()).save(book);

    }

    private Book createValidBook() {
        return Book.builder().title("my book").author("author").ISBN("123456").build();
    }

}
