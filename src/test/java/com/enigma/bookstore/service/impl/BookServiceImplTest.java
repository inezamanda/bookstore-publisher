package com.enigma.bookstore.service.impl;

import com.enigma.bookstore.entity.Book;
import com.enigma.bookstore.repository.BookRepository;
import com.enigma.bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookServiceImplTest {

    @MockBean
    BookRepository bookRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookService bookService;

    @Test
    void addBook() {
        Book book = new Book("A001", "cinderella", "description", "publisher", 2020, 200, "Indonesia", 40, 30000);
        bookService.addBook(book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void getBookById() {
        when(bookRepository.findById("A001")).thenReturn(java.util.Optional.of(new Book("A001", "cinderella", "description", "publisher", 2020, 200, "Indonesia", 40, 30000)));

        Book book = bookService.getBookById("A001");

        assertEquals("cinderella", book.getTitle());
        assertEquals("description", book.getDescription());
    }

    @Test
    void getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        Book book = new Book("A001", "cinderella", "description", "publisher", 2020, 200, "Indonesia", 40, 30000);
        Book book2 = new Book("A002", "cinderella", "description", "publisher", 2020, 200, "Indonesia", 40, 30000);
        Book book3 = new Book("A003", "cinderella", "description", "publisher", 2020, 200, "Indonesia", 40, 30000);

        books.add(book);
        books.add(book2);
        books.add(book3);

        when(bookRepository.findAll()).thenReturn(books);

        // Test
        List<Book> bookList = bookService.getAllBooks();
        assertEquals(3, bookList.size());
        verify(bookRepository, times(1)).findAll();
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
    void getBooksByYear() {
    }
}