package com.example.epolsoftbackend.book.DTO;

import com.example.epolsoftbackend.topic.DTO.TopicResponseDTO;
import com.example.epolsoftbackend.user.DTO.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@Setter
@Getter
public class BookUpdateDTO {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("topic")
    private TopicResponseDTO topicResponseDTO;
    @JsonProperty("user")
    private UserResponseDTO userResponseDTO;
    @JsonProperty("description")
    private String description;
    @JsonProperty("shortDescription")
    private String shortDescription;
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("filePath")
    private String filePath;
}
