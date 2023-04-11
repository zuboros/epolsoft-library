package com.example.epolsoftbackend.book.DTO;

import com.example.epolsoftbackend.topic.DTO.TopicResponseDTO;
import com.example.epolsoftbackend.user.DTO.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
public class BookCreateDTO {

    private String name;

    @JsonProperty("topic")
    private TopicResponseDTO topicResponseDTO;

    @JsonProperty("user")
    private UserResponseDTO userResponseDTO;

    private String description;

    private String shortDescription;

    private String fileName;

    private String filePath;
}
