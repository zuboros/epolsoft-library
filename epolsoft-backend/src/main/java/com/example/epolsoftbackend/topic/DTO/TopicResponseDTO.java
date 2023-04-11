package com.example.epolsoftbackend.topic.DTO;

import lombok.*;

@Builder
@Data
public class TopicResponseDTO {

    private long id;

    private String name;

    private boolean isActive;

}
