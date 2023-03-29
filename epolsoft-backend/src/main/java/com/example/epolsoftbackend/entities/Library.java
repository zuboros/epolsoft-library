package com.example.epolsoftbackend.entities;

import lombok.Getter;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.annotation.ReadOnlyProperty;

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

    @Column(name = "file")
    private String file;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public long getId(){
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public String getFile() {
        return file;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getTopic() {
        return topic;
    }
}
