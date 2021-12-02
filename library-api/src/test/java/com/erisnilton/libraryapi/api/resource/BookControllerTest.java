package com.erisnilton.libraryapi.api.resource;

import com.erisnilton.libraryapi.api.dto.BookDTO;
import com.erisnilton.libraryapi.api.model.Book;
import com.erisnilton.libraryapi.api.service.BookService;
import com.erisnilton.libraryapi.exception.BussinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest()
@AutoConfigureMockMvc()
public class BookControllerTest {

    static String  BOOK_API = "/api/books";

    @Autowired
    MockMvc mvc;

    @MockBean
    BookService bookService;

    @Test
    @DisplayName("Should be created book with success")
    public void createBookTest() throws Exception {
        BookDTO dto = createNewBook();

        Book bookSave = Book.builder()
                .id(001L)
                .title("my book")
                .author("author")
                .ISBN("123456")
                .build();

        String json = new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(bookService.save(Mockito.any(Book.class))).willReturn(bookSave);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("id").value(001))
                .andExpect(jsonPath("title").value(dto.getTitle()))
                .andExpect(jsonPath("author").value(dto.getAuthor()))
                .andExpect(jsonPath("isbn").value(dto.getIsbn()));



    }


    @Test
    @DisplayName("Should throw error of validation when there is existing data insufficient for create book")
    public void createIvalidBookTest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new BookDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(3)));

    }

    @Test
    @DisplayName("Should throw erros if try create book with dupliacated isbn")
    public void createBookWithDuplicationIsbn() throws Exception {
        BookDTO dto = createNewBook();
        String json = new ObjectMapper().writeValueAsString(dto);
        String errorMessage = "Isbn already registered.";

        BDDMockito.given(bookService.save(Mockito.any(Book.class)))
                .willThrow(new BussinessException(errorMessage));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(errorMessage));

    }
    @Test
    @DisplayName("Should get information the a book")
    public void getBookDetailsTest() throws Exception{
        // Given
        Long id  = 1L;
        Book book = Book.builder()
                .id(id)
                .title(createNewBook().getTitle())
                .author(createNewBook().getAuthor())
                .ISBN(createNewBook().getIsbn()).build();

        BDDMockito.given(bookService.getById(id)).willReturn(Optional.of(book));

        // When
        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get(BOOK_API.concat("/" + id)).accept(MediaType.APPLICATION_JSON);


        // then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("title").value(book.getTitle()))
                .andExpect(jsonPath("author").value(book.getAuthor()))
                .andExpect(jsonPath("isbn").value(book.getISBN()));
    }
    @Test
    @DisplayName("Should return resource not found, when the book search does not exists")
    public void bookNotFoundTest() throws Exception {
        BDDMockito.given(bookService.getById(anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get(BOOK_API.concat("/" + 1)).accept(MediaType.APPLICATION_JSON);

        mvc.perform(request).andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("Should delete a book")
    public void deleteBookTest() throws Exception {

        BDDMockito.given(bookService.getById(anyLong())).willReturn(Optional.of(Book.builder().id(1L).build()));

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .delete(BOOK_API.concat("/" + 1));

        mvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return not found resource when try delete book that not exists")
    public void deleteInexistentBookTest() throws Exception {

        BDDMockito.given(bookService.getById(anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .delete(BOOK_API.concat("/" + 1));

        mvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should update a book")
    public void updateBookTest() throws Exception{
        Long id = 1l;
        String json = new ObjectMapper().writeValueAsString(createNewBook());
        Book book = Book.builder().id(id).title("some title").author("some author").ISBN("1213456").build();

        BDDMockito.given(bookService.getById(id)).willReturn(Optional.of(book));

        BDDMockito.given(bookService.update(book)).willReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(BOOK_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("title").value(book.getTitle()))
                .andExpect(jsonPath("author").value(book.getAuthor()))
                .andExpect(jsonPath("isbn").value(book.getISBN()));

    }

    @Test
    @DisplayName("Should return not found when try update a book inexistent")
    public void updateInexistentBookTest() throws Exception{
        String json = new ObjectMapper().writeValueAsString(createNewBook());

        BDDMockito.given(bookService.getById(anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(BOOK_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request).andExpect(status().isNotFound());

    }


    private BookDTO createNewBook() {
        return BookDTO.builder()
                .title("my book")
                .author("author")
                .isbn("123456")
                .build();
    }
}
