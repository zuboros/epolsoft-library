package com.example.epolsoftbackend.author;

import java.io.Serializable;
import java.util.Set;

import com.example.epolsoftbackend.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "author")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    @Pattern(regexp = "^[ a-zA-zа-яА-Я.]{5,100}$")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "authorId", cascade = CascadeType.ALL)
    private Set<Book> books;

}
