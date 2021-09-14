package com.enigma.bookstore.service;

import com.enigma.bookstore.entity.Book;

import java.util.List;

public interface BookService {
    public Book addBook(Book book);
    public Book getBookById(Integer id);
    public List<Book> getAllBooks();
    public Book updateBook(Book book);
    public void deleteBook(Integer id);
}
