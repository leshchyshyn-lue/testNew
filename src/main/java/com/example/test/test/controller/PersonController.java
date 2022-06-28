package com.example.test.test.controller;


import com.example.test.test.dto.PersonDTO;
import com.example.test.test.entity.Person;
import com.example.test.test.service.PersonService;
import com.example.test.test.util.PersonWithThatLastNameAlreadyExists;
import com.example.test.test.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
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
    public Person updatePerson(@PathVariable("id") Long id,
                               @RequestBody PersonDTO updatedPersonDTO)
            throws UserNotFoundException, PersonWithThatLastNameAlreadyExists {
        return personService.updatePerson(id, personService.convertToPerson(updatedPersonDTO));
    }

    @PostMapping
    public Person addPerson(@RequestBody PersonDTO personDTO)
            throws PersonWithThatLastNameAlreadyExists {
        return personService.addPerson(personService.convertToPerson(personDTO));
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") Long id) throws UserNotFoundException {
        personService.deletePerson(id);
        return "Person deleted";
    }


}
