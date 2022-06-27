package com.example.test.test.controller;


import com.example.test.test.dto.BookDTO;
import com.example.test.test.entity.Book;
import com.example.test.test.service.BookService;
import com.example.test.test.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/library")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<?> findAllBooks() {
        try {
            return ResponseEntity.ok(bookService.findAllBooks());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Eror");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(bookService.findById(id));
        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Eror");
        }
    }

    @PutMapping("/{id}/update") ////Не добавив ДТО
    public ResponseEntity<?> updateBook(@PathVariable("id") Long id,
                                     @RequestBody Book book) {
        try {
            return ResponseEntity.ok(bookService.updateBook(id, book));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Eror");
        }
    }


    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody BookDTO bookDTO) {
        try {
            return ResponseEntity.ok(bookService.addBook(bookService.convertToBook(bookDTO)));
        } catch (BookWithThisNameAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Eror");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok("Book deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Eror");
        }
    }

    @PutMapping("/{personId}/get/{bookId}") ////Не добавив ДТО
    public ResponseEntity<?> getBook(@PathVariable Integer personId, @PathVariable Integer bookId) {
        try {
            return ResponseEntity.ok(bookService.getBook(Long.valueOf(personId), Long.valueOf(bookId)));
        } catch (BookIsBooked e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Eror");
        }
    }

    @PutMapping("/{id}/put")
    public ResponseEntity<?> putBook(@PathVariable("id") Long bookId) {
        try {
            bookService.putBook(bookId);
            return ResponseEntity.ok("You have successfully placed the book");
        } catch (BookNotFoundException | YouDontHaveThisBook e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Eror");
        }
    }
}
