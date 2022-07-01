package com.example.test.test.controller;


import com.example.test.test.entity.Book;
import com.example.test.test.request.BookRequest;
import com.example.test.test.response.BookResponse;
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

    public BookResponse convertToResponse(Book book) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setNameBook(book.getNameBook());
        bookResponse.setNameBook(book.getNameBook());
        bookResponse.setStatus(book.isStatus());
        bookResponse.setId(book.getId());
        return bookResponse;
    }

    public Book convertToBook(BookRequest bookRequest) {
        Book book = new Book();
        book.setNameBook(bookRequest.getNameBook());
        book.setAutor(bookRequest.getAutor());
        return book;
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
    public BookResponse updateBook(@PathVariable("id") Long id,
                                   @RequestBody BookRequest bookRequest) throws BookNotFoundException, BookWithThisNameAlreadyExists {
        return convertToResponse(bookService.updateBook(id, convertToBook(bookRequest)));
    }


    @PostMapping
    public BookResponse addBook(@RequestBody BookRequest bookRequest) throws BookWithThisNameAlreadyExists {
        return convertToResponse(bookService.addBook(convertToBook(bookRequest)));
    }


    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return "Book deleted";
    }

    @PutMapping("/{personId}/get/{bookId}")
    public Book getBook(@PathVariable Integer personId, @PathVariable Integer bookId) throws BookIsBooked, UserNotFoundException, BookNotFoundException {
        return bookService.getBook(Long.valueOf(personId), Long.valueOf(bookId));
    }

    @PutMapping("/{id}/put")
    public Book putBook(@PathVariable("id") Long bookId) throws BookNotFoundException, YouDontHaveThisBook {
        return bookService.putBook(bookId);
    }
}
