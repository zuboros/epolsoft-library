package com.example.epolsoftbackend.library;

import lombok.Getter;
import org.springframework.data.annotation.Immutable;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Immutable
@Entity(name = "library")
public class Library {
    @Id
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "topic")
    private String topic;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
