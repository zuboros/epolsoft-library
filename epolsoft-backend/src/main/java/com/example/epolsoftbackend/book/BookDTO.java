package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.author.AuthorDTO;
import com.example.epolsoftbackend.topic.TopicDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@Setter
@Getter
public class BookDTO {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("topic")
    private TopicDTO topicDTO;
    @JsonProperty("author")
    private AuthorDTO authorDTO;
    @JsonProperty("description")
    private String description;
    @JsonProperty("shortDescription")
    private String shortDescription;
    @JsonProperty("file")
    private String file;
}
