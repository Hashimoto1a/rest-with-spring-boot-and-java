package br.com.hashimoto1a.repository;

import br.com.hashimoto1a.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
