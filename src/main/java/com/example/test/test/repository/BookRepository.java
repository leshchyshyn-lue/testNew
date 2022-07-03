package com.example.test.test.repository;

import com.example.test.test.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByNameBook(String bookName);

}
