package br.com.lucas.demo.repositories;

import br.com.lucas.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PersonRepository extends JpaRepository<Person, Long> { }
