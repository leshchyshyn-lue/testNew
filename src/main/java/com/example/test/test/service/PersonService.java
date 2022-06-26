package com.example.test.test.service;


import com.example.test.test.entity.Person;
import com.example.test.test.repository.PersonRepository;
import com.example.test.test.util.PersonWithThatFirstNameAlreadyExists;
import com.example.test.test.util.PersonWithThatLastNameAlreadyExists;
import com.example.test.test.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person findById(Long id) throws UserNotFoundException {
        Person person = personRepository.findById(id).get();
        if (person == null) {
            throw new UserNotFoundException("No person with this ID was found");
        }
        return person;
    }

    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }


    public Person updatePerson(Long id, Person updatedPerson) throws UserNotFoundException {
        Person person = findById(id);
        person.setFirstName(updatedPerson.getFirstName());
        person.setLastName(updatedPerson.getLastName());
        return personRepository.save(person);
    }

    public Person addPerson(Person person) throws PersonWithThatFirstNameAlreadyExists, PersonWithThatLastNameAlreadyExists {
        if (personRepository.findByFirstName(person.getFirstName()) != null) {
            throw new PersonWithThatFirstNameAlreadyExists("Person with that first name already exists");
        }
        if (personRepository.findByLastName(person.getLastName()) != null) {
            throw new PersonWithThatLastNameAlreadyExists("Person with that last name already exists");
        }
        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }


    //git создай, добав
}
