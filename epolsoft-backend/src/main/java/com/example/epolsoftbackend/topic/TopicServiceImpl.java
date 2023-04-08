package com.example.epolsoftbackend.topic;

import com.example.epolsoftbackend.topic.DTO.TopicCreateDTO;
import com.example.epolsoftbackend.topic.DTO.TopicResponseDTO;
import com.example.epolsoftbackend.topic.DTO.TopicUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    public ResponseEntity<TopicResponseDTO> createTopic(TopicCreateDTO topicCreateDTO){
        try {
            return new ResponseEntity<>(topicMapper.topicToTopicResponseDTO(
                    topicRepository.saveAndFlush(topicMapper.topicCreateDTOToTopic(topicCreateDTO))),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<Topic> findById(Long id) {
        return topicRepository.findById(id);
    }

    public ResponseEntity<List> getAllTopics(Pageable pageable){
        Page page = topicRepository.findAll(pageable);
        List result = List.of(page.getContent(), page.getTotalElements());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<List<TopicResponseDTO>> getAllAvailableTopics() {
        return new ResponseEntity<>(topicMapper.listTopicToListTopicResponseDTO(topicRepository.findAll(new Specification<Topic>() {
            @Override
            public Predicate toPredicate(Root<Topic> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(criteriaBuilder.equal(root.get("is_active"), true));
            }
        })), HttpStatus.OK);
        }

    public ResponseEntity<TopicResponseDTO> updateTopic(TopicUpdateDTO topicUpdateDTO) {
        Topic topicForUpdate = topicMapper.topicUpdateDTOtoTopic(topicUpdateDTO);
        Topic actualTopic = topicRepository.findById(topicForUpdate.getId()).get();

        if (actualTopic.isActive()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        actualTopic.setName(topicForUpdate.getName());

        return new ResponseEntity<>(topicMapper.topicToTopicResponseDTO(
                topicRepository.saveAndFlush(actualTopic)),
                HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteById(long id) {
        try {
            if (!topicRepository.findById(id).get().isActive()) {
                topicRepository.deleteById(id);

                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
