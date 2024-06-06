package com.alura.literaluraweb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByDeathYear(String year);
    Optional<Author> findByName(String name);

}
