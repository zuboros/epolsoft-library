package com.example.epolsoftbackend.topic.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicUpdateDTO {

    private long id;

    private String name;

}
