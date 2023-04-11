package com.example.epolsoftbackend.topic;

import com.example.epolsoftbackend.topic.DTO.TopicCreateDTO;
import com.example.epolsoftbackend.topic.DTO.TopicResponseDTO;
import com.example.epolsoftbackend.topic.DTO.TopicUpdateDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public interface TopicService {

    Optional<Topic> findById(Long id);
    List<TopicResponseDTO> getAllAvailableTopics();
    List getAllTopics(Pageable pageable);
    TopicResponseDTO createTopic(TopicCreateDTO topicCreateDTO);
    boolean deleteById(long id);
    TopicResponseDTO updateTopic(TopicUpdateDTO topicCreateDTO);

}
