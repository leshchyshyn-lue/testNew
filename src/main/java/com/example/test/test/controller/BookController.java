package com.example.test.test.controller;


import com.example.test.test.entity.Book;
import com.example.test.test.service.BookService;
import com.example.test.test.util.BookIsBooked;
import com.example.test.test.util.BookNotFoundException;
import com.example.test.test.util.BookWithThisNameAlreadyExists;
import com.example.test.test.util.YouDontHaveThisBook;
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
    public ResponseEntity findAllBooks() {
        try {
            return ResponseEntity.ok(bookService.findAllBooks());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eror");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok(bookService.findById(id));
        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eror");
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity updateBook(@PathVariable("id") Long id,
                                     @RequestBody Book book){
        try {
            return ResponseEntity.ok(bookService.updateBook(id, book));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eror");
        }
    }


    @PostMapping
    public ResponseEntity addBook(@RequestBody Book book) {
        try {
            return ResponseEntity.ok(bookService.addBook(book));
        } catch (BookWithThisNameAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eror");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok("Book deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eror");
        }
    }

    @PutMapping("/{personId}/get/{bookId}")
    public ResponseEntity getBook(@PathVariable Integer personId, @PathVariable Integer bookId) {
        try {
            return ResponseEntity.ok(bookService.getBook(Long.valueOf(personId), Long.valueOf(bookId)));
        } catch (BookIsBooked e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eror");
        }
    }

    @PutMapping("/{id}/put")
    public ResponseEntity putBook(@PathVariable("id") Long bookId)  {
        try {
            bookService.putBook(bookId);
            return ResponseEntity.ok("You have successfully placed the book");
        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (YouDontHaveThisBook e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eror");
        }
    }
}
