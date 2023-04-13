package com.example.epolsoftbackend.book.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDTO {

    private String name;

    private long topicId;

    private long userId;

    private String description;

    private String shortDescription;

    private String fileName;

    private String filePath;
}
