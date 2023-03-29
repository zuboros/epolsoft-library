package com.example.epolsoftbackend.services;

import com.example.epolsoftbackend.entities.Topic;
import com.example.epolsoftbackend.repositories.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic create(String name){
        Topic topicToSave = new Topic();
        topicToSave.setName(name);
        return topicRepository.saveAndFlush(topicToSave);
    }

    public Optional<Topic> findById(long id){
        return topicRepository.findById(id);
    }
}
