package com.example.epolsoftbackend.services;

import com.example.epolsoftbackend.entities.Topic;
import com.example.epolsoftbackend.repositories.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ResponseEntity<List<Topic>> getAll(){
        return new ResponseEntity<>(topicRepository.findAll(), HttpStatus.OK);
    }
    public Topic searchExistingOrCreateNew(Long id, String name){
        if(id != null) {
            return this.findById(id).orElse(new Topic(name));
        }
        else return this.create(name);
    }
}
