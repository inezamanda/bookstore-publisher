package com.enigma.bookstore.service.impl;

import com.enigma.bookstore.constant.ResponseMessage;
import com.enigma.bookstore.entity.Book;
import com.enigma.bookstore.repository.BookRepository;
import com.enigma.bookstore.service.BookService;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Integer id) {
        verify(id);
        return bookRepository.findById(id).get();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(Book book) {
        verify(book.getId());
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Integer id) {
        verify(id);
        bookRepository.deleteById(id);
    }

    private void verify (Integer id) {
        if (!bookRepository.findById(id).isPresent()){
            String message = String.format(ResponseMessage.NOT_FOUND_MESSAGE, "book", id);
            throw new ResourceNotFoundException(message);
        }
    }
}
