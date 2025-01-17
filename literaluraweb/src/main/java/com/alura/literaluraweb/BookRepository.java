package com.alura.literaluraweb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository <Book, Long>{
    List<Book> findByLanguage(String language);

}
