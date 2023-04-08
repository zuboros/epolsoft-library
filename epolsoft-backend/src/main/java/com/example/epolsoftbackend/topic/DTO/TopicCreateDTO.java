package com.example.epolsoftbackend.topic.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@ToString
@Data
@Getter
@Setter
public class TopicCreateDTO {

    @JsonProperty("name")
    private String name;

}
