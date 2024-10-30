package br.com.lucas.demo.repositories;

import br.com.lucas.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> { }
