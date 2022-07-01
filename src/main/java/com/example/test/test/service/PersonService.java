package com.example.test.test.service;


import com.example.test.test.entity.Person;
import com.example.test.test.repository.PersonRepository;
import com.example.test.test.request.PersonRequest;
import com.example.test.test.util.PersonWithThatLastNameAlreadyExists;
import com.example.test.test.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonRequest personRequest;


    @Autowired
    public PersonService(PersonRepository personRepository, PersonRequest personRequest) {
        this.personRepository = personRepository;
        this.personRequest = personRequest;
    }

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
        List<Person> personList = findAllPersons();
        for (Person p : personList) {
            String lastName = p.getLastName();
            if (lastName.equalsIgnoreCase(String.valueOf(updatedPerson.getLastName()))) {
                throw new PersonWithThatLastNameAlreadyExists("Person with this last name already exists");
            }
        }
        person.setFirstName(updatedPerson.getFirstName());
        person.setLastName(updatedPerson.getLastName());
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
