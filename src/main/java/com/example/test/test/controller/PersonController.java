package com.example.test.test.controller;


import com.example.test.test.entity.Person;
import com.example.test.test.service.PersonService;
import com.example.test.test.util.PersonWithThatFirstNameAlreadyExists;
import com.example.test.test.util.PersonWithThatLastNameAlreadyExists;
import com.example.test.test.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            Person person = personService.findById(id);
            return ResponseEntity.ok(person);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eror");
        }
    }

    @GetMapping
    public ResponseEntity findAllPersons() {
        try {
            return ResponseEntity.ok(personService.findAllPersons());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eror");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePerson(@PathVariable("id") Long id,
                                       @RequestBody Person updatedPerson) throws UserNotFoundException {
        try {
            return ResponseEntity.ok(personService.updatePerson(id, updatedPerson));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eror");
        }
    }

    @PostMapping
    public ResponseEntity addPerson(@RequestBody Person person) {
        try {
            return ResponseEntity.ok(personService.addPerson(person));
        } catch (PersonWithThatFirstNameAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (PersonWithThatLastNameAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eror");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable("id") Long id) {
        try {
            personService.deletePerson(id);
            return ResponseEntity.ok("Person deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eror");
        }
    }

}