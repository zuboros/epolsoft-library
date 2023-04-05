package com.example.epolsoftbackend.topic;

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

    @GetMapping("/get/topics")
    public ResponseEntity<List<TopicDTO>> getAllTopics(){
        return topicService.getAllTopics();
    }
}
