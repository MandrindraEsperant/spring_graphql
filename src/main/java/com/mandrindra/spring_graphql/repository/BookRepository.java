package com.mandrindra.spring_graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mandrindra.spring_graphql.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
