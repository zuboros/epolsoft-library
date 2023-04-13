package com.example.epolsoftbackend.book.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateDTO {

    private long id;

    private String name;

    private long topicId;

    private long userId;

    private String description;

    private String shortDescription;

    private String fileName;

    private String filePath;
}
