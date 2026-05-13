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

import com.mandrindra.spring_graphql.input.BookInput;
import com.mandrindra.spring_graphql.model.Author;
import com.mandrindra.spring_graphql.model.Book;
import com.mandrindra.spring_graphql.repository.AuthorRepository;
import com.mandrindra.spring_graphql.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookController bookController;

    private Author author;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setFirstName("Victor");
        author.setLastName("Hugo");

        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Les Misérables");
        book1.setPageCount(1500);
        book1.setAuthor(author);

        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Notre-Dame de Paris");
        book2.setPageCount(940);
        book2.setAuthor(author);
    }

    // ─── allBooks ─────────────────────────────────────────────

    @Test
    void allBooks_shouldReturnAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> result = bookController.allBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Les Misérables", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    // ─── bookById ─────────────────────────────────────────────

    @Test
    void bookById_shouldReturnBook_whenExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        Book result = bookController.bookById(1L);

        assertNotNull(result);
        assertEquals("Les Misérables", result.getTitle());
    }

    @Test
    void bookById_shouldReturnNull_whenNotExists() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        Book result = bookController.bookById(99L);

        assertNull(result);
    }

    // ─── createBook ───────────────────────────────────────────

    @Test
    void createBook_shouldSaveBook_withAuthor() {
        BookInput input = new BookInput();
        input.setTitle("Les Misérables");
        input.setPageCount(1500);
        input.setAuthorId(1L);

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        Book result = bookController.createBook(input);

        assertNotNull(result);
        assertEquals("Les Misérables", result.getTitle());
        assertEquals(1500, result.getPageCount());
        verify(authorRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void createBook_shouldSaveBook_withoutAuthor() {
        BookInput input = new BookInput();
        input.setTitle("Les Misérables");
        input.setPageCount(1500);
        input.setAuthorId(null);

        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        Book result = bookController.createBook(input);

        assertNotNull(result);
        verify(authorRepository, never()).findById(any());
    }

    // ─── updateBook ───────────────────────────────────────────

    @Test
    void updateBook_shouldUpdateAndReturn_whenExists() {
        BookInput input = new BookInput();
        input.setTitle("Les Misérables - Edition révisée");
        input.setPageCount(1600);
        input.setAuthorId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        Book result = bookController.updateBook(1L, input);

        assertNotNull(result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBook_shouldReturnNull_whenNotExists() {
        BookInput input = new BookInput();
        input.setTitle("Titre");
        input.setPageCount(100);

        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        Book result = bookController.updateBook(99L, input);

        assertNull(result);
        verify(bookRepository, never()).save(any());
    }

    // ─── deleteBook ───────────────────────────────────────────

    @Test
    void deleteBook_shouldReturnTrue_whenSuccess() {
        doNothing().when(bookRepository).deleteById(1L);

        boolean result = bookController.deleteBook(1L);

        assertTrue(result);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBook_shouldReturnFalse_whenException() {
        doThrow(new RuntimeException("Erreur")).when(bookRepository).deleteById(99L);

        boolean result = bookController.deleteBook(99L);

        assertFalse(result);
    }
}