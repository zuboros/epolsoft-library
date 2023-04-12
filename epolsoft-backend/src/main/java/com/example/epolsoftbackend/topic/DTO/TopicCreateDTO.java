package com.example.epolsoftbackend.topic.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicCreateDTO {

    private String name;

}
