package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface Personrepository extends CrudRepository<Person,Long> {
    Person findByname(String name);
}
