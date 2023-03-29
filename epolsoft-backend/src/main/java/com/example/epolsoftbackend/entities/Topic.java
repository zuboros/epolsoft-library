package com.example.epolsoftbackend.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    @Pattern(regexp = "^[ a-zA-zа-яА-Я]{4,100}$")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "topicId", cascade = CascadeType.ALL)
    private Set<Book> books;

    public Topic(String name) {
        this.name = name;
    }
}
