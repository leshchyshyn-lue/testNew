package com.example.test.test.service;


import com.example.test.test.entity.Person;
import com.example.test.test.repository.PersonRepository;
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


    //ПРотести
    public Person findById(Long id) throws UserNotFoundException {
        return personRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("No person with this id was found"));
    }

    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }


    public Person updatePerson(Long id, Person updatedPerson) throws UserNotFoundException, PersonWithThatLastNameAlreadyExists {
        Person person = findById(id);
        person.setFirstName(updatedPerson.getFirstName());
        person.setLastName(updatedPerson.getLastName());
        if (personRepository.findByLastName(person.getLastName()) != null) {
            throw new PersonWithThatLastNameAlreadyExists("Person with this last name already exists");
        }
        return personRepository.save(person);
    }

    public Person addPerson(Person newPerson) throws PersonWithThatLastNameAlreadyExists {
        Person person = personRepository.findByLastName(newPerson.getLastName());
        if (person != null) {
            throw new PersonWithThatLastNameAlreadyExists
                    ("Person with that last name already exists");
        }
        return personRepository.save(newPerson);
    }

    public void deletePerson(Long id) throws UserNotFoundException {
        findById(id);
        personRepository.deleteById(id);
    }


}
