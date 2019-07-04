package com.rajanu.graphql.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Book {

    @Id
    private String isn;
    private String title;
    private String publisher;

    @OneToMany(mappedBy = "book",
            cascade = CascadeType.ALL
    )
    private Set<Author> authors;

    private String publishedDate;
}
