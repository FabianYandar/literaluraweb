package com.alura.literaluraweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class MenuService {
    private final BookService bookService;

    @Autowired
    public MenuService(BookService bookService) {
        this.bookService = bookService;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menú:");
            System.out.println("1 - Buscar libro por título");
            System.out.println("2 - Listar libros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos en un determinado año");
            System.out.println("5 - Listar libros por idioma");
            System.out.println("0 - Salir");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume la nueva línea

            switch (choice) {
                case 1:
                    System.out.print("Ingrese el título del libro: ");
                    String title = scanner.nextLine();
                    String result = bookService.findBookByTitleAndSave(title)
                            .map(book -> "Libro encontrado y guardado: " + book.getTitle())
                            .orElse("Libro no encontrado");
                    System.out.println(result);
                    break;
                case 2:
                    bookService.listAllBooks().forEach(book -> System.out.println(book.getTitle()));
                    break;
                case 3:
                    bookService.listAllAuthors().forEach(author -> System.out.println(author.getName()));
                    break;
                case 4:
                    System.out.print("Ingrese el año: ");
                    String year = scanner.nextLine();
                    bookService.listAuthorsAliveInYear(year).forEach(author -> System.out.println(author.getName()));
                    break;
                case 5:
                    System.out.print("Ingrese el idioma: ");
                    String language = scanner.nextLine();
                    bookService.listBooksByLanguage(language).forEach(book -> System.out.println(book.getTitle()));
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
}
