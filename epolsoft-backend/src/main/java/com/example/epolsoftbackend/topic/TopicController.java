package com.example.epolsoftbackend.topic;

import com.example.epolsoftbackend.topic.DTO.TopicCreateDTO;
import com.example.epolsoftbackend.topic.DTO.TopicResponseDTO;
import com.example.epolsoftbackend.topic.DTO.TopicUpdateDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicController {
    final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List> getAllTopics(Pageable pageable){
        return new ResponseEntity<>(topicService.getAllTopics(pageable), HttpStatus.OK);
    }

    @GetMapping("/get/available")
    public ResponseEntity<List<TopicResponseDTO>> getAllAvailableTopics() {
        return new ResponseEntity<>(topicService.getAllAvailableTopics(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<TopicResponseDTO> createTopic(TopicCreateDTO topicCreateDTO) {
        return new ResponseEntity<>(topicService.createTopic(topicCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<TopicResponseDTO> updateTopic(TopicUpdateDTO topicUpdateDTO) {
        return new ResponseEntity<>(topicService.updateTopic(topicUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTopic(@PathVariable("id") long id) {
        topicService.deleteById(id);
    }
}

