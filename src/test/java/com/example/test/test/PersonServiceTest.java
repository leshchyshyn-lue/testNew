package com.example.test.test;


import com.example.test.test.entity.Book;
import com.example.test.test.entity.Person;
import com.example.test.test.repository.PersonRepository;
import com.example.test.test.service.PersonService;
import com.example.test.test.util.PersonWithThatLastNameAlreadyExists;
import com.example.test.test.util.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {
    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;

    private static final String FIRST_NAME = "SomeName";
    private static final String LAST_NAME = "SomeLastName";
    private static final Long PERSON_ID = 1L;

    @Test
    public void testAddPersonSuccess() throws PersonWithThatLastNameAlreadyExists {
        Person person = new Person();
        person.setFirstName(FIRST_NAME);
        person.setLastName(LAST_NAME);

        Person after = new Person();
        after.setFirstName(FIRST_NAME);
        after.setLastName(LAST_NAME);
        after.setId(PERSON_ID);

        when(personRepository.findByLastName(person.getLastName())).thenReturn(null);
        when(personRepository.save(person)).thenReturn(after);

        Person result = personService.addPerson(person);

        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());
    }

    @Test
    public void testAddPersonFailWithPersonWithThatLastNameAlreadyExists() {
        Person person = new Person();
        person.setLastName(LAST_NAME);

        when(personRepository.findByLastName(person.getLastName())).thenReturn(new Person());

        assertThrows(PersonWithThatLastNameAlreadyExists.class, () -> personService.addPerson(person));
    }

    @Test
    public void testFindByIdFailUserNotFoundException() {
        Person person = new Person();
        person.setId(PERSON_ID);

        when(personRepository.findById(person.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> personService.findById(person.getId()));
    }

    @Test
    public void testFindById() throws UserNotFoundException {
        Person person = new Person();
        person.setId(PERSON_ID);

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        Person result = personService.findById(person.getId());

        assertEquals(person, result);
    }

    @Test
    public void testFindAllPersonsSuccess() {
        Person person = new Person();
        person.setId(PERSON_ID);

        List<Person> people = new ArrayList<>();
        people.add(person);

        when(personRepository.findAll()).thenReturn(people);

        List<Person> result = personService.findAllPersons();

        assertEquals(people, result);
    }

    @Test
    public void testUpdatePersonSuccess() throws UserNotFoundException, PersonWithThatLastNameAlreadyExists {
        Person person = new Person();
        person.setFirstName(FIRST_NAME);
        person.setLastName(LAST_NAME);
        person.setId(PERSON_ID);

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(personRepository.findByLastName(person.getLastName())).thenReturn(null);
        when(personRepository.save(person)).thenReturn(person);

        Person result = personService.updatePerson(person.getId(), person);

        assertEquals(result.getFirstName(), person.getFirstName());
        assertEquals(result.getLastName(), person.getLastName());
    }

    @Test
    public void testUpdatePersonFailPersonWithThatLastNameAlreadyExists() {
        Person person = new Person();
        person.setLastName(LAST_NAME);
        person.setId(PERSON_ID);


        when(personRepository.findByLastName(person.getLastName())).thenReturn(new Person());
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));


        assertThrows(PersonWithThatLastNameAlreadyExists.class, () -> personService.updatePerson(person.getId(), person));

    }

    @Test
    public void testDeletePersonSuccess() {
        Person person = new Person();
        person.setId(PERSON_ID);

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        assertDoesNotThrow(() -> personService.deletePerson(person.getId()));
    }


}
