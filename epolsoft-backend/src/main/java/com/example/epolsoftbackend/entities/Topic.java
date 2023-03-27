package com.example.epolsoftbackend.entities;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "topic")
public class Topic implements Serializable{

    public Topic( String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    @Pattern(regexp = "^[a-zA-zа-яА-Я]{4,100}$")
    private String name;

    @OneToMany(mappedBy = "topicId")
    private Set<Book> book;
}
