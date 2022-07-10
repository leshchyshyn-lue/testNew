package com.example.test.test.controller;


import com.example.test.test.entity.Book;
import com.example.test.test.request.BookRequest;
import com.example.test.test.response.BookResponse;
import com.example.test.test.service.BookService;
import com.example.test.test.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/library")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    private BookResponse convertToResponse(Book book) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setNameBook(book.getNameBook());
        bookResponse.setAutor(book.getAutor());
        bookResponse.setId(book.getId());
        return bookResponse;
    }

    private Book convertToBook(BookRequest bookRequest) {
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
    public BookResponse findById(@PathVariable("id") Long id) throws BookNotFoundException {
        return convertToResponse(bookService.findById(id));
    }

    @PutMapping("/{id}/update")
    public BookResponse updateBook(@PathVariable("id") Long id,
                                   @RequestBody @Valid BookRequest bookRequest) throws BookNotFoundException, BookWithThisNameAlreadyExists {
        return convertToResponse(bookService.updateBook(id, convertToBook(bookRequest)));
    }


    @PostMapping
    public BookResponse addBook(@RequestBody @Valid BookRequest bookRequest) throws BookWithThisNameAlreadyExists {
        return convertToResponse(bookService.addBook(convertToBook(bookRequest)));
    }


    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") Long id) throws BookNotFoundException {
        bookService.deleteBook(id);
        return "Book deleted";
    }

    @PutMapping("/{personId}/get/{bookId}")
    public BookResponse getBook(@PathVariable Integer personId, @PathVariable Integer bookId) throws BookIsBooked, UserNotFoundException, BookNotFoundException {
        return convertToResponse(bookService.getBook(Long.valueOf(personId), Long.valueOf(bookId)));
    }

    @PutMapping("/{id}/put")
    public BookResponse putBook(@PathVariable("id") Long bookId) throws BookNotFoundException, YouDontHaveThisBook {
        return convertToResponse(bookService.putBook(bookId));
    }
}
