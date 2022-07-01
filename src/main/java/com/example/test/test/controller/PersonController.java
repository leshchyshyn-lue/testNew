package com.example.test.test.controller;


import com.example.test.test.entity.Person;
import com.example.test.test.request.PersonRequest;
import com.example.test.test.response.PersonResponse;
import com.example.test.test.service.PersonService;
import com.example.test.test.util.PersonWithThatLastNameAlreadyExists;
import com.example.test.test.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;


    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    public PersonResponse convertToResponse(Person person) {
        PersonResponse personResponse = new PersonResponse();
        personResponse.setFirstName(person.getFirstName());
        personResponse.setLastName(person.getLastName());
        personResponse.setId(person.getId());
        return personResponse;
    }

    public Person convertToPerson(PersonRequest personRequest) {
        Person person = new Person();
        person.setFirstName(personRequest.getFirstName());
        person.setLastName(personRequest.getLastName());
        return person;
    }


    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") Long id) throws UserNotFoundException {
        return personService.findById(id);
    }

    @GetMapping
    public List<Person> findAllPersons() {
        return personService.findAllPersons();
    }

    @PutMapping("/{id}")
    public PersonResponse updatePerson(@PathVariable("id") Long id,
                                       @RequestBody @Valid PersonRequest personRequest) throws UserNotFoundException, PersonWithThatLastNameAlreadyExists {
        Person person = personService.updatePerson(id, convertToPerson(personRequest));
        return convertToResponse(person);
    }

    @PostMapping
    public PersonResponse addPerson(@RequestBody @Valid PersonRequest personRequest) throws PersonWithThatLastNameAlreadyExists {
        Person person = personService.addPerson(convertToPerson(personRequest));

        return convertToResponse(person);
    }


    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") Long id) throws UserNotFoundException {
        personService.deletePerson(id);
        return "Person deleted";
    }


}
