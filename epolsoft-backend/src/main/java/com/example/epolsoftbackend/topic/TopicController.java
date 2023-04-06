package com.example.epolsoftbackend.topic;

import com.example.epolsoftbackend.topic.DTO.TopicCreateDTO;
import com.example.epolsoftbackend.topic.DTO.TopicResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return topicService.getAllTopics(pageable);
    }

    @GetMapping("/get/available")
    public ResponseEntity<List<TopicResponseDTO>> getAllAvailableTopics(){
        return topicService.getAllAvailableTopics();
    }

    @PostMapping("/create")
    public ResponseEntity<TopicResponseDTO> createTopic(TopicCreateDTO topicCreateDTO){
        return topicService.createTopic(topicCreateDTO);
    }
}

