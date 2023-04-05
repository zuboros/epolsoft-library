package com.example.epolsoftbackend.topic;

import com.example.epolsoftbackend.author.Author;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicService(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    public ResponseEntity<List<TopicDTO>> getAllTopics(){
        return new ResponseEntity<>(topicMapper.listTopicToListTopicDTO(topicRepository.findAll()), HttpStatus.OK);
    }
}
