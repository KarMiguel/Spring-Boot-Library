package io.github.karMiguel.library.repository;

import io.github.karMiguel.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
