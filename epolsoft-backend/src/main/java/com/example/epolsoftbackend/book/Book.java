package com.example.epolsoftbackend.book;

import java.io.Serializable;

import com.example.epolsoftbackend.author.Author;
import com.example.epolsoftbackend.topic.Topic;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @Pattern(regexp = "^[ a-zA-zа-яА-Я]{2,100}$")
    private String name;

    @ManyToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    private Topic topicId;

    @OneToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author authorId;

    @Column(name = "description")
    @Pattern(regexp = "^[ a-zA-zа-яА-Я.,-]{0,255}$")
    private String description;

    @Column(name = "short_description")
    @Pattern(regexp = "^[ a-zA-zа-яА-Я.,-]{0,150}$")
    private String shortDescription;

    @Column(name = "file")
    @Pattern(regexp = "^[a-zA-zа-яА-Я.,_-]{0,255}$")
    private String file;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
