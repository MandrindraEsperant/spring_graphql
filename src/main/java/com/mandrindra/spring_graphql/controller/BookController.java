package com.mandrindra.spring_graphql.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.mandrindra.spring_graphql.input.BookInput;
import com.mandrindra.spring_graphql.model.Book;
import com.mandrindra.spring_graphql.repository.AuthorRepository;
import com.mandrindra.spring_graphql.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookController{
	private final AuthorRepository authorRepository;
	private final BookRepository bookRepository;
	
	@QueryMapping
	public List<Book> allBooks() {
	    return bookRepository.findAll();
	   }
	   
	@QueryMapping
	public Book bookById(@Argument Long id) {
	    return bookRepository.findById(id).orElse(null);
	  }
	  
	@MutationMapping
	public Book createBook(@Argument BookInput bookInput) {
	    var book = new Book();
	    book.setTitle(bookInput.getTitle());
	    book.setPageCount(bookInput.getPageCount());
	    
	    if (bookInput.getAuthorId() != null) {
	    	authorRepository.findById(bookInput.getAuthorId()).ifPresent(book::setAuthor);
	    }
	    
	    return	 bookRepository.save(book);
	 }
	
	@MutationMapping
	public Book updateBook(@Argument Long id, @Argument BookInput bookInput) {
		return bookRepository.findById(id)
				.map(existingBook ->{
					existingBook.setTitle(bookInput.getTitle());
					existingBook.setPageCount(bookInput.getPageCount());
					
					if(bookInput.getAuthorId()!= null) {
						authorRepository.findById(bookInput.getAuthorId()).ifPresent(existingBook::setAuthor);
					}
					return bookRepository.save(existingBook);
					
				}).orElse(null);
	 }	
	
	@MutationMapping
	public boolean deleteBook(@Argument Long id){
		try {
			 bookRepository.deleteById(id);
			 return true;
		}catch(Exception e) {
			return false;
		}
	}
	    		 
}
