package com.example.epolsoftbackend.topic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@ToString
@Data
@Getter
@Setter
public class TopicDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;
}
