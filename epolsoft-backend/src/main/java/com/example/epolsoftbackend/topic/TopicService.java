package com.example.epolsoftbackend.topic;

import com.example.epolsoftbackend.topic.DTO.TopicCreateDTO;
import com.example.epolsoftbackend.topic.DTO.TopicResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TopicService {

    Optional<Topic> findById(Long id);
    ResponseEntity<List<TopicResponseDTO>> getAllAvailableTopics();
    ResponseEntity<List> getAllTopics(Pageable pageable);
    ResponseEntity<TopicResponseDTO> createTopic(TopicCreateDTO topicCreateDTO);

}
