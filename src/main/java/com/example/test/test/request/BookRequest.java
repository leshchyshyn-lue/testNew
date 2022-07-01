package com.example.test.test.request;

import javax.validation.constraints.Size;

public class BookRequest {

    @Size(min = 3, max = 30, message = "Name book should be between 3 and 30")
    private String nameBook;

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
