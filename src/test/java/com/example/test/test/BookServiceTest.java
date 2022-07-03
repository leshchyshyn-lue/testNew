package com.example.test.test;

import com.example.test.test.entity.Book;
import com.example.test.test.entity.Person;
import com.example.test.test.repository.BookRepository;
import com.example.test.test.service.BookService;
import com.example.test.test.service.PersonService;
import com.example.test.test.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Mock
    private PersonService personService;

    private static final String BOOK_NAME = "Some bookName";
    private static final String AUTOR = "Some autor";
    private static final Long BOOK_ID = 1L;

    private static final Long Person_ID = 1L;


    @Test
    public void testFindAllBooks() {

        Book book = new Book();

        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findAll()).thenReturn(books);
        List<Book> result = bookService.findAllBooks();

        assertEquals(books, result);
    }

    @Test
    public void testFindByIdSuccess() throws BookNotFoundException {
        Book book = new Book();
        book.setId(BOOK_ID);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Book result = bookService.findById(book.getId());
        assertEquals(book, result);
    }

    @Test
    public void testFindDyIdFailBookNotFoundException() {
        Book book = new Book();
        book.setId(BOOK_ID);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.findById(book.getId()));
    }

    @Test
    public void testUpdateBookSuccess() throws BookNotFoundException, BookWithThisNameAlreadyExists {
        Book before = new Book();
        before.setNameBook(BOOK_NAME);
        before.setAutor(AUTOR);
        before.setId(BOOK_ID);

        Book after = new Book();
        after.setNameBook(BOOK_NAME);
        after.setAutor(AUTOR);
        after.setId(BOOK_ID);

        when(bookRepository.findById(before.getId())).thenReturn(Optional.of(before));
        when(bookRepository.findByNameBook(before.getNameBook())).thenReturn(null);
        when(bookRepository.save(before)).thenReturn(after);

        Book result = bookService.updateBook(after.getId(), after);

        assertEquals(after.getNameBook(), result.getNameBook());
        assertEquals(after.getAutor(), result.getAutor());
    }

    @Test
    public void testUpdateBookFailBookWithThisNameAlreadyExists() {
        Book book = new Book();
        book.setNameBook(BOOK_NAME);
        book.setId(BOOK_ID);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.findByNameBook(book.getNameBook())).thenReturn(new Book());

        assertThrows(BookWithThisNameAlreadyExists.class, () -> bookService.updateBook(book.getId(), book));
    }

    @Test
    public void testAddBookSuccess() throws BookWithThisNameAlreadyExists {
        Book before = new Book();
        before.setNameBook(BOOK_NAME);
        before.setAutor(AUTOR);

        Book after = new Book();
        after.setNameBook(BOOK_NAME);
        after.setAutor(AUTOR);
        after.setId(BOOK_ID);

        when(bookRepository.findByNameBook(before.getNameBook())).thenReturn(null);
        when(bookRepository.save(before)).thenReturn(after);

        Book result = bookService.addBook(before);

        assertEquals(result.getNameBook(), before.getNameBook());
        assertEquals(result.getAutor(), before.getAutor());
    }

    @Test
    public void testAddBookFailBookWithThisNameAlreadyExists() {
        Book book = new Book();
        book.setNameBook(BOOK_NAME);
        book.setAutor(AUTOR);

        when(bookRepository.findByNameBook(book.getNameBook())).thenReturn(new Book());

        assertThrows(BookWithThisNameAlreadyExists.class, () -> bookService.addBook(book));
    }

    @Test
    public void testGetBookSuccess() throws UserNotFoundException, BookIsBooked, BookNotFoundException {
        Book before = new Book();
        before.setNameBook(BOOK_NAME);
        before.setAutor(AUTOR);
        before.setId(BOOK_ID);
        before.setPersonId(null);

        Person person = new Person();
        person.setId(Person_ID);

        Book after = new Book();
        after.setNameBook(BOOK_NAME);
        after.setAutor(AUTOR);
        after.setId(BOOK_ID);
        after.setPersonId(person);

        when(bookRepository.findById(before.getId())).thenReturn(Optional.of(before));
        when(personService.findById(person.getId())).thenReturn(person);
        when(bookRepository.save(before)).thenReturn(after);
        Book result = bookService.getBook(person.getId(), after.getId());

        assertEquals(result, after);
    }

    @Test
    public void getBookFailBookIsBooked() {
        Person person = new Person();
        person.setId(Person_ID);

        Book book = new Book();
        book.setPersonId(person);
        book.setId(BOOK_ID);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        assertThrows(BookIsBooked.class, () -> bookService.getBook(person.getId(), book.getId()));
    }


    @Test
    public void testPutBookSuccess() throws BookNotFoundException, YouDontHaveThisBook {
        Book before = new Book();
        before.setId(BOOK_ID);
        before.setPersonId(new Person());

        Book after = new Book();
        after.setId(BOOK_ID);
        after.setPersonId(null);

        when(bookRepository.findById(before.getId())).thenReturn(Optional.of(before));
        when(bookRepository.save(before)).thenReturn(after);

        Book result = bookService.putBook(before.getId());

        assertEquals(result.getPersonId(), after.getPersonId());
    }

    @Test
    public void putBookFailBookNotFoundException() {
        Book book = new Book();
        book.setId(BOOK_ID);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.putBook(book.getId()));
    }

    @Test
    public void putBookFailYouDontHaveThisBook() {
        Book book = new Book();
        book.setPersonId(null);
        book.setId(BOOK_ID);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        assertThrows(YouDontHaveThisBook.class, () -> bookService.putBook(book.getId()));
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book();
        book.setId(BOOK_ID);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        assertDoesNotThrow(() -> bookService.deleteBook(book.getId()));
    }

}
