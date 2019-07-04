package com.rajanu.graphql.example.service;

import com.rajanu.graphql.example.model.Author;
import com.rajanu.graphql.example.model.Book;
import com.rajanu.graphql.example.repository.BookRepository;
import com.rajanu.graphql.example.service.datafetcher.AllBooksDataFetcher;
import com.rajanu.graphql.example.service.datafetcher.AuthorsDataFetcher;
import com.rajanu.graphql.example.service.datafetcher.BookDataFetcher;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

@Service
public class GraphQLService {

    @Autowired
    BookRepository bookRepository;

    @Value("classpath:books.graphql")
    Resource resource;

    private GraphQL graphQL;
    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetcher;
    @Autowired
    private AuthorsDataFetcher allAuthorsDataFetcher;

    // load schema at application start up
    @PostConstruct
    private void loadSchema() throws IOException {

        //Load Books into the Book Repository
        loadDataIntoHSQL();

        // get the schema
        File schemaFile = resource.getFile();
        // parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    // loads seed data in the database
    private void loadDataIntoHSQL() {
        Book bookOfClouds = new Book("123", "Book of Clouds", "Kindle Edition",
                new HashSet<>(Arrays.asList( ) ),
                "Nov 2017");
        Book cloudArchEngineering = new Book("124", "Cloud Arch & Engineering", "Orielly",
                new HashSet<>(Arrays.asList( ) ),
                "Jan 2015");
        Book somethingGreat = new Book("125", "Something Great", "Orielly",
                new HashSet<>(Arrays.asList() ),
                "Dec 2016");

        Author chloe = Author.builder().book(bookOfClouds).fname("Chloe").lname("Aridjis").build();
        Author peter = Author.builder().book(cloudArchEngineering).fname("Peter").lname("nollen").build();
        Author rajan = Author.builder().book(cloudArchEngineering).book(somethingGreat).fname("Rajan").lname("upadhyay").build();

        bookOfClouds.getAuthors().add(chloe);
        cloudArchEngineering.getAuthors().add(peter);
        somethingGreat.getAuthors().add(rajan);

        Stream.of(bookOfClouds,cloudArchEngineering,somethingGreat).forEach(book -> {
            bookRepository.save(book);
        });
    }

    private RuntimeWiring buildRuntimeWiring() {

        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allBooks", allBooksDataFetcher)
                        .dataFetcher("book", bookDataFetcher)
                        .dataFetcher("allAuthors", allAuthorsDataFetcher)
                )
                .build();
    }


    public GraphQL getGraphQL() {
        return graphQL;
    }
}
