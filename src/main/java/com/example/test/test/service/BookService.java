package com.example.test.test.service;

import com.example.test.test.entity.Book;
import com.example.test.test.entity.Person;
import com.example.test.test.repository.BookRepository;
import com.example.test.test.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {

    private final BookRepository bookRepository;

    private final PersonService personService;

    @Autowired
    public BookService(BookRepository bookRepository, PersonService personService) {
        this.bookRepository = bookRepository;
        this.personService = personService;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id).get();
        if (book == null) {
            throw new BookNotFoundException("No book with this ID was found");
        }
        return book;
    }

    public Book updateBook(Long id, Book updatedBook) throws BookNotFoundException {
        Book book = findById(id);
        book.setNameBook(updatedBook.getNameBook());
        book.setAutor(updatedBook.getAutor());
        return bookRepository.save(book);
    }

    public Book addBook(Book addedBook) throws BookWithThisNameAlreadyExists {
        if (bookRepository.findByNameBook(addedBook.getNameBook()) != null) {
            throw new BookWithThisNameAlreadyExists("A book with this name already exists");
        }
        return bookRepository.save(addedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


    public Book getBook(Long personId, Long bookId) throws BookNotFoundException, BookIsBooked, UserNotFoundException {
        Book book = findById(bookId);
        Person person = personService.findById(personId);
        if (book.isStatus() == false) {
            book.setPersonId(person);
            book.setStatus(true);
        } else {
            throw new BookIsBooked("This book is booked");
        }
        return bookRepository.save(book);
    }

    public Book putBook(Long bookId) throws BookNotFoundException, YouDontHaveThisBook {
        Book book = findById(bookId);
        if (book == null) {
            throw new BookNotFoundException("No book with this ID was found");
        }
        if (book.getPersonId() == null){
            throw new YouDontHaveThisBook("You don't have this book");
        }
        book.setPersonId(null);
        book.setStatus(false);
        return bookRepository.save(book);
    }


}