package com.mandrindra.spring_graphql.config;

import com.mandrindra.spring_graphql.model.Author;
import com.mandrindra.spring_graphql.model.Book;
import com.mandrindra.spring_graphql.repository.AuthorRepository;
import com.mandrindra.spring_graphql.repository.BookRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(AuthorRepository authorRepository, BookRepository bookRepository) {
        return args -> {

            // --- Auteurs ---
            Author author1 = new Author();
            author1.setFirstName("Victor");
            author1.setLastName("Hugo");

            Author author2 = new Author();
            author2.setFirstName("Albert");
            author2.setLastName("Camus");

            Author author3 = new Author();
            author3.setFirstName("Antoine");
            author3.setLastName("de Saint-Exupéry");

            authorRepository.save(author1);
            authorRepository.save(author2);
            authorRepository.save(author3);

            // --- Livres ---
            Book book1 = new Book();
            book1.setTitle("Les Misérables");
            book1.setPageCount(1900);
            book1.setAuthor(author1);

            Book book2 = new Book();
            book2.setTitle("Notre-Dame de Paris");
            book2.setPageCount(940);
            book2.setAuthor(author1);

            Book book3 = new Book();
            book3.setTitle("L'Étranger");
            book3.setPageCount(185);
            book3.setAuthor(author2);

            Book book4 = new Book();
            book4.setTitle("La Peste");
            book4.setPageCount(412);
            book4.setAuthor(author2);

            Book book5 = new Book();
            book5.setTitle("Le Petit Prince");
            book5.setPageCount(96);
            book5.setAuthor(author3);

            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);
            bookRepository.save(book4);
            bookRepository.save(book5);

            System.out.println("✅ Données initiales chargées avec succès !");
        };
    }
}