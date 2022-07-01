package com.example.test.test.response;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PersonResponse {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty(message = "First name should not be empty")
    @Size(min = 3, max = 30, message = "First name should be between 3 and 30")
    private String firstName;

    @NotNull
    @NotEmpty(message = "Last name should not be empty")
    @Size(min = 3, max = 30, message = "Last name should be between 3 and 30")
    private String lastName;

    public PersonResponse() {

    }


    public PersonResponse(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
