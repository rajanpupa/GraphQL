package com.rajanu.graphql.example.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fname;
    private String lname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "isn",
            foreignKey = @ForeignKey(name = "FK_BOOK_AUTHOR"))
    private Book book;
}
