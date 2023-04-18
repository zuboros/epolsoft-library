package com.example.epolsoftbackend.topic;

import com.example.epolsoftbackend.topic.DTO.TopicCreateDTO;
import com.example.epolsoftbackend.topic.DTO.TopicResponseDTO;
import com.example.epolsoftbackend.topic.DTO.TopicUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/topic")
@RequiredArgsConstructor
public class TopicController {
    final TopicService topicService;

    @GetMapping("/get/all")
    public ResponseEntity<List> getAllTopics(Pageable pageable){
        return new ResponseEntity<>(topicService.getAllTopics(pageable), HttpStatus.OK);
    }

    @GetMapping("/get/available")
    public ResponseEntity<List<TopicResponseDTO>> getAllAvailableTopics() {
        return new ResponseEntity<>(topicService.getAllAvailableTopics(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<TopicResponseDTO> createTopic(@RequestBody TopicCreateDTO topicCreateDTO) {
        return new ResponseEntity<>(topicService.createTopic(topicCreateDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<TopicResponseDTO> updateTopic(@RequestBody TopicUpdateDTO topicUpdateDTO) {
        return new ResponseEntity<>(topicService.updateTopic(topicUpdateDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/disable/{id}")
    public ResponseEntity<TopicResponseDTO> disableTopic(@PathVariable Long id){
        return new ResponseEntity<>(topicService.disableTopic(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enable/{id}")
    public ResponseEntity<TopicResponseDTO> enableTopic(@PathVariable Long id){
        return new ResponseEntity<>(topicService.enableTopic(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTopic(@PathVariable("id") long id) {
        topicService.deleteById(id);
    }
}

