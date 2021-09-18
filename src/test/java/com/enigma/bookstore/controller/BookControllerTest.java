package com.enigma.bookstore.controller;

import com.enigma.bookstore.entity.Book;
import com.enigma.bookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    BookService bookService;

    @Autowired
    BookController bookController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createBook() {
        when(bookService.addBook(any(Book.class))).thenReturn(new Book("A001", "cinderella", "description", "publisher", 2020, 200, "Indonesia", 40, 30000));

        Book book = new Book("A001", "cinderella", "description", "publisher", 2020, 200, "Indonesia", 40, 30000);

        Book book1 = bookController.createBook(book);
        assertThat(book1.getTitle()).isEqualTo("cinderella");
    }

    // Positive Test Case
    @Test
    void createBookWithResponseHeader() throws Exception {
        Book book = new Book("A001", "cinderella", "description", "publisher", 2020, 200, "Indonesia", 40, 30000);
        given(bookService.addBook(any(Book.class))).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        this.mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(book)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.is(book.getTitle())));
    }

    // Negative Test Case
    @Test
    void createBookWithHeaderStatus400() throws Exception {
        this.mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is4xxClientError());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findBookById() {
    }

    @Test
    void updateBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void getBookPerPage() {
    }

    @Test
    void searchBookByTitle() {
    }

    @Test
    void searchBookByYear() {
    }
}