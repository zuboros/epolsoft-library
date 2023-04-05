package com.example.epolsoftbackend.topic;

import com.example.epolsoftbackend.topic.DTO.TopicResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TopicService {

    Optional<Topic> findById(Long id);
    ResponseEntity<List<TopicResponseDTO>> getAllTopics();

}
