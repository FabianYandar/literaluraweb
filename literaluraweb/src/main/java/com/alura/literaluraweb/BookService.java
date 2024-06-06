package com.alura.literaluraweb;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
public class BookService {
    private final RestTemplate restTemplate;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(RestTemplate restTemplate, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.restTemplate = restTemplate;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Optional<Book> findBookByTitleAndSave(String title) {
        String url = "https://gutendex.com/books/?search=" + title;
        BookResponse response = restTemplate.getForObject(url, BookResponse.class);

        if (response != null) {
            Optional<BookDTO> bookDTO = response.getResults().stream()
                    .filter(book -> title.equalsIgnoreCase(book.getTitle()))
                    .findFirst();

            if (bookDTO.isPresent()) {
                BookDTO dto = bookDTO.get();

                Book book = new Book();
                book.setTitle(dto.getTitle());
                book.setLanguage(dto.getLanguage());
                book.setDownloadCount(dto.getDownload_count());

                Set<Author> authors = new HashSet<>();
                for (AuthorDTO authorDTO : dto.getAuthors()) {
                    Author author = authorRepository.findByName(authorDTO.getName())
                            .orElseGet(() -> {
                                Author newAuthor = new Author();
                                newAuthor.setName(authorDTO.getName());
                                newAuthor.setBirthYear(authorDTO.getBirth_year());
                                newAuthor.setDeathYear(authorDTO.getDeath_year());
                                return authorRepository.save(newAuthor);
                            });
                    authors.add(author);
                }
                book.setAuthors(authors);
                bookRepository.save(book);

                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public List<Author> listAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> listAuthorsAliveInYear(String year) {
        return authorRepository.findByDeathYear(year);
    }

    public List<Book> listBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }
}
