package com.maculuve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maculuve.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{
    
}
