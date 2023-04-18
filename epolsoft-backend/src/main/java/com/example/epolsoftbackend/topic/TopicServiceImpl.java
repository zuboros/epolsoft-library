package com.example.epolsoftbackend.topic;

import com.example.epolsoftbackend.exception.BadRequestException;
import com.example.epolsoftbackend.exception.ResourceNotFoundException;
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

    public TopicResponseDTO createTopic(TopicCreateDTO topicCreateDTO){
        try {
            return topicMapper.topicToTopicResponseDTO(topicRepository.saveAndFlush(topicMapper.topicCreateDTOToTopic(topicCreateDTO)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalError("Error with creating topic with name " + topicCreateDTO.getName());
        }
    }

    public Optional<Topic> findById(Long id) {
        return topicRepository.findById(id);
    }

    public List getAllTopics(Pageable pageable){
        Page<Topic> page = topicRepository.findAll(pageable);
        return List.of(page.getContent(), page.getTotalElements());
    }

    public List<TopicResponseDTO> getAllAvailableTopics() {
        return topicMapper.listTopicToListTopicResponseDTO(topicRepository.findAll(new Specification<Topic>() {
            @Override
            public Predicate toPredicate(Root<Topic> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(criteriaBuilder.equal(root.get("isActive"), true));
            }
        }));
    }

    public TopicResponseDTO updateTopic(TopicUpdateDTO topicUpdateDTO) {
        Topic actualTopic = topicRepository.findById(topicMapper.topicUpdateDTOtoTopic(topicUpdateDTO).getId()).orElseThrow(
                () -> new ResourceNotFoundException("Topic", "id", topicMapper.topicUpdateDTOtoTopic(topicUpdateDTO).getId()));

            if (actualTopic.isActive()) {
                throw new BadRequestException("Topic: " + actualTopic + " has used and can't be changed");
            }

            actualTopic.setName(topicMapper.topicUpdateDTOtoTopic(topicUpdateDTO).getName());

            return topicMapper.topicToTopicResponseDTO(topicRepository.saveAndFlush(actualTopic));
    }

    public TopicResponseDTO disableTopic(Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Topic", "id", id));
        topic.setActive(false);
        return topicMapper.topicToTopicResponseDTO(topicRepository.saveAndFlush(topic));
    }

    public TopicResponseDTO enableTopic(Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Topic", "id", id));
        topic.setActive(true);
        return topicMapper.topicToTopicResponseDTO(topicRepository.saveAndFlush(topic));
    }

    public void deleteById(long id) {
        try {
            Topic topic = topicRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Topic", "id", id));
            if (!topic.isActive()) {
                topicRepository.deleteById(id);
            } else {
                throw new InternalError("Topic used or not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalError("Error with find or delete topic");
        }
    }
}
