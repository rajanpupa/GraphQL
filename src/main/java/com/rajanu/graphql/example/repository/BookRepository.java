package com.rajanu.graphql.example.repository;

import com.rajanu.graphql.example.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
