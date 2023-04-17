package com.example.epolsoftbackend.book.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailedDTO {

    private long id;

    private String name;

    private String topic;

    private String user;

    private String description;

    private String shortDescription;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;

}
