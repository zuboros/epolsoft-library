package com.example.epolsoftbackend.library;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Column(name = "topic_name")
    private String topic;

    @Column(name = "status")
    private String status;

    @Column(name = "user_name")
    private String author;

    @Column(name = "user_id")
    private String authorId;

    @Column(name = "description")
    private String description;

    @Column(name = "short_description")
    private String shortDescription;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
