package com.mandrindra.spring_graphql.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mandrindra.spring_graphql.input.AuthorInput;
import com.mandrindra.spring_graphql.model.Author;
import com.mandrindra.spring_graphql.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorController authorController;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        author1 = new Author();
        author1.setId(1L);
        author1.setFirstName("Victor");
        author1.setLastName("Hugo");

        author2 = new Author();
        author2.setId(2L);
        author2.setFirstName("Albert");
        author2.setLastName("Camus");
    }

    // ─── allAuthors ───────────────────────────────────────────

    @Test
    void allAuthors_shouldReturnAllAuthors() {
        // GIVEN
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        // WHEN
        List<Author> result = authorController.allAuthors();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Victor", result.get(0).getFirstName());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void allAuthors_shouldReturnEmptyList() {
        when(authorRepository.findAll()).thenReturn(List.of());

        List<Author> result = authorController.allAuthors();

        assertTrue(result.isEmpty());
    }

    // ─── authorById ───────────────────────────────────────────

    @Test
    void authorById_shouldReturnAuthor_whenExists() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));

        Author result = authorController.authorById(1L);

        assertNotNull(result);
        assertEquals("Hugo", result.getLastName());
    }

    @Test
    void authorById_shouldReturnNull_whenNotExists() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        Author result = authorController.authorById(99L);

        assertNull(result);
    }

    // ─── createAuthor ─────────────────────────────────────────

    @Test
    void createAuthor_shouldSaveAndReturnAuthor() {
        // GIVEN
        AuthorInput input = new AuthorInput();
        input.setFirstName("Victor");
        input.setLastName("Hugo");

        when(authorRepository.save(any(Author.class))).thenReturn(author1);

        // WHEN
        Author result = authorController.createAuthor(input);

        // THEN
        assertNotNull(result);
        assertEquals("Victor", result.getFirstName());
        assertEquals("Hugo", result.getLastName());
        verify(authorRepository, times(1)).save(any(Author.class));
    }
}