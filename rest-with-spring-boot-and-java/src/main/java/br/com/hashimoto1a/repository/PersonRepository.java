package br.com.hashimoto1a.repository;

import br.com.hashimoto1a.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
