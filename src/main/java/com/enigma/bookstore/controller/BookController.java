package com.enigma.bookstore.controller;

import com.enigma.bookstore.entity.Book;
import com.enigma.bookstore.service.BookService;
import com.enigma.bookstore.utils.PageResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping
    public Book createBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @GetMapping("/{id}")
    public Book findBookById(@PathVariable String id) {
        return bookService.getBookById(id);
    }

//    @GetMapping
//    public List<Book> findAllBooks() {
//        return bookService.getAllBooks();
//    }

    @PutMapping()
    public Book updateBook(@RequestBody Book book){
        return bookService.updateBook(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
    }

    @GetMapping
    public PageResponseWrapper<Book> getBookPerPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "3") Integer size,
                                                    @RequestParam(name = "sortBy", defaultValue = "title") String sortBy,
                                                    @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable paging = PageRequest.of(page, size, sort);
        Page<Book> bookPage = bookService.getBookPerPage(paging);
        return new PageResponseWrapper<>(bookPage);
    }

    @GetMapping("/search")
    public List<Book> searchBookByTitle (@RequestParam String title) {
        return bookService.searchBookByTitle(title);
    }

    @GetMapping("/year/{year}")
    public List<Book> searchBookByYear (@PathVariable Integer year) {
        return bookService.getBooksByYear(year);
    }
}
