package com.example.test.test.repository;

import com.example.test.test.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByLastName(String lastName);
}
