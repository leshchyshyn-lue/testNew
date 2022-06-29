package com.example.test.test.entity;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "First name should not be empty")
    @Size(min = 3, max = 30, message = "First name should be between 3 and 30")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    @Size(min = 3, max = 30, message = "Last name should be between 3 and 30")
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "personId")
    private List<Book> books;

    public Person() {

    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


}
