package com.example.epolsoftbackend.topic;

import com.example.epolsoftbackend.topic.DTO.TopicResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicService(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    public Optional<Topic> findById(Long id) {
        return topicRepository.findById(id);
    }

    public ResponseEntity<List<TopicResponseDTO>> getAllTopics(){
        return new ResponseEntity<>(topicMapper.listTopicToListTopicResponseDTO(topicRepository.findAll()), HttpStatus.OK);
    }
}
