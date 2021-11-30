package com.erisnilton.libraryapi.api.resource;

import com.erisnilton.libraryapi.api.dto.BookDTO;
import com.erisnilton.libraryapi.api.model.Book;
import com.erisnilton.libraryapi.api.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        BookDTO dto = BookDTO.builder()
                .title("my book")
                .author("author")
                .isbn("123456")
                .build();

        Book bookSave = Book.builder()
                .id(001L)
                .title("my book")
                .author("author")
                .isbn("123456")
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
    public void createIvalidBookTest() {}
}
