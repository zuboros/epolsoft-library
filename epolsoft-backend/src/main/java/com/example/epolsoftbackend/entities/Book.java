package com.example.epolsoftbackend.entities;

import java.io.Serializable;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    @Pattern(regexp = "^[ a-zA-zа-яА-Я]{2,100}$")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    private Topic topicId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @Column(name = "description")
    @Pattern(regexp = "^[ a-zA-zа-яА-Я]{0,100}$")
    private String description;

    @Column(name = "short_description")
    @Pattern(regexp = "^[ a-zA-zа-яА-Я]{0,100}$")
    private String shortDescription;

    @Column(name = "file")
    private String file;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
