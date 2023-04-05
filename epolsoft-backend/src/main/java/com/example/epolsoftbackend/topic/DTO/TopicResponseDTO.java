package com.example.epolsoftbackend.topic.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@ToString
@Data
@Getter
@Setter
public class TopicResponseDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("isActive")
    private boolean isActive;

    //maybe time in response
}
