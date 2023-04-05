package com.example.epolsoftbackend.topic;

import com.example.epolsoftbackend.topic.DTO.TopicResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicController {
    final TopicServiceImpl topicService;

    public TopicController(TopicServiceImpl topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/get/topics")
    public ResponseEntity<List<TopicResponseDTO>> getAllTopics(){
        return topicService.getAllTopics();
    }
}

