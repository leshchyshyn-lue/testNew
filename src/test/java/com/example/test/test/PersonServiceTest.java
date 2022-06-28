package com.example.test.test;

import com.example.test.test.entity.Person;
import com.example.test.test.service.PersonService;
import com.example.test.test.util.PersonWithThatLastNameAlreadyExists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {
    public PersonServiceTest() {

    }

    private Person person1;
    private Person person2;
    private Person person3;
    @Autowired
    private PersonService personService;


    @Before
    public void setUp() {
        person1 = new Person("firstNameOne", "lastNameOne");
        person2 = new Person("firstNameTwo", "lastNameTwo");
        person3 = new Person("firstNameThree", "lastNameThree");
    }

    @Test
    public void checkFindAllAndAddPerson() throws PersonWithThatLastNameAlreadyExists {
        List<Person> expected = personService.findAllPersons();

        List<Person> actual = new ArrayList<>();
        for (Person p : expected) {
            actual.add(p);
        }
        actual.add(person1);
        actual.add(person2);
        actual.add(person3);

        expected.add(personService.addPerson(person1));
        expected.add(personService.addPerson(person2));
        expected.add(personService.addPerson(person3));

        Assert.assertEquals(expected, actual);
    }


}
