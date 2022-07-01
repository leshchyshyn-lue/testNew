package com.example.test.test.request;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
public class BookRequest {

    @NotNull
    @NotEmpty(message = "Book name should not be empty")
    @Size(min = 3, max = 30, message = "Name book should be between 3 and 30")
    private String nameBook;
    @NotNull
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 3, max = 30, message = "autor should be between 3 and 30")
    private String autor;

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
