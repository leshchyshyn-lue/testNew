package com.example.test.test.controller;


import com.example.test.test.dto.BookDTO;
import com.example.test.test.entity.Book;
import com.example.test.test.service.BookService;
import com.example.test.test.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/library")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable("id") Long id) throws BookNotFoundException {
        return bookService.findById(id);
    }

    @PutMapping("/{id}/update")
    public Book updateBook(@PathVariable("id") Long id,
                           @RequestBody BookDTO bookDTO)
            throws BookNotFoundException, BookWithThisNameAlreadyExists {
        return bookService.updateBook(id, bookService.convertToBook(bookDTO));
    }


    @PostMapping
    public Book addBook(@RequestBody BookDTO bookDTO) throws BookWithThisNameAlreadyExists {
        return bookService.addBook(bookService.convertToBook(bookDTO));
    }


    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return "Book deleted";
    }

    @PutMapping("/{personId}/get/{bookId}")
    public Book getBook(@PathVariable Integer personId, @PathVariable Integer bookId)
            throws BookIsBooked, UserNotFoundException, BookNotFoundException {
        return bookService.getBook(Long.valueOf(personId), Long.valueOf(bookId));
    }

    @PutMapping("/{id}/put")
    public Book putBook(@PathVariable("id") Long bookId) throws BookNotFoundException, YouDontHaveThisBook {
        return bookService.putBook(bookId);
    }
}
