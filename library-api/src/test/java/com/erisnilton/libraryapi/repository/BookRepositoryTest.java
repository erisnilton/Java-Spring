package com.erisnilton.libraryapi.repository;

import com.erisnilton.libraryapi.api.model.Book;
import com.erisnilton.libraryapi.api.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("Should return true when there is a book in the base with isbn informed")
    public void returnTrueWhenIsbnExists() {
        // given
        String isbn = "123456";
        Book book = Book.builder().title("My Book").author("Author").ISBN(isbn).build();
        entityManager.persist(book);

        // when
        boolean existsIsbn = repository.existsByISBN(isbn);

        // Then
        assertThat(existsIsbn).isTrue();

    }
    @Test
    @DisplayName("Should return False when  does not there is sbn informed")
    public void returnFalseWhenIsbnDoesExists() {
        // given
        String isbn = "123456";

        // when
        boolean existsIsbn = repository.existsByISBN(isbn);

        // Then
        assertThat(existsIsbn).isFalse();

    }
}
