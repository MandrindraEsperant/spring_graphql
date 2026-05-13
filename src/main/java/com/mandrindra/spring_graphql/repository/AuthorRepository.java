package com.mandrindra.spring_graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mandrindra.spring_graphql.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
