package com.rajanu.graphql.example.service.datafetcher;

import com.rajanu.graphql.example.model.Author;
import com.rajanu.graphql.example.repository.AuthorRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorsDataFetcher implements DataFetcher<List<Author>> {
    @Autowired
    AuthorRepository authorRepository;

    @Override
    public List<Author> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return authorRepository.findAll();
    }
}
