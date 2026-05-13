package com.mandrindra.spring_graphql.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.mandrindra.spring_graphql.input.AuthorInput;
import com.mandrindra.spring_graphql.input.BookInput;
import com.mandrindra.spring_graphql.model.Author;
import com.mandrindra.spring_graphql.model.Book;
import com.mandrindra.spring_graphql.repository.AuthorRepository;
import com.mandrindra.spring_graphql.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthorController {
	private final AuthorRepository authorRepository;
	
	@QueryMapping
	public List<Author> allAuthors() {
	    return authorRepository.findAll();
	   }
	   
	@QueryMapping
	public Author authorById(@Argument Long id) {
	    return authorRepository.findById(id).orElse(null);
	  }
	
	@MutationMapping
	public Author createAuthor(@Argument AuthorInput authorInput) {
	    var author = new Author();
	    author.setFirstName(authorInput.getFirstName());
	    author.setLastName(authorInput.getLastName());
	    
	    return authorRepository.save(author);
	}
}
