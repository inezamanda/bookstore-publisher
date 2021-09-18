package com.enigma.bookstore.service.impl;

import com.enigma.bookstore.constant.ResponseMessage;
import com.enigma.bookstore.entity.Book;
import com.enigma.bookstore.repository.BookRepository;
import com.enigma.bookstore.service.BookService;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Book getBookById(String id) {
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
    public void deleteBook(String id) {
        verify(id);
        bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> getBookPerPage(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public List<Book> searchBookByTitle(String title) {
        return bookRepository.findBookByTitleContaining(title);
    }

    @Override
    public List<Book> getBooksByYear(Integer year) {
        return bookRepository.findBookByYear(year);
    }

    private void verify (String id) {
        if (!bookRepository.findById(id).isPresent()){
            String message = String.format(ResponseMessage.NOT_FOUND_MESSAGE, "book", id);
            throw new ResourceNotFoundException(message);
        }
    }
}
