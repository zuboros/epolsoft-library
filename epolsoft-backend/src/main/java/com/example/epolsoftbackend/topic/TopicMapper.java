package com.example.epolsoftbackend.topic;

import com.example.epolsoftbackend.topic.DTO.TopicCreateDTO;
import com.example.epolsoftbackend.topic.DTO.TopicResponseDTO;
import com.example.epolsoftbackend.topic.DTO.TopicUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    public TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    //TopicResponse
    @Mapping(source = "active", target = "isActive")

    public TopicResponseDTO topicToTopicResponseDTO(Topic topic);
    public Topic topicResponseDTOToTopic(TopicResponseDTO topicResponseDTO);
    public List<TopicResponseDTO> listTopicToListTopicResponseDTO(List<Topic> topicList);

    //TopicCreate
    public TopicCreateDTO topicToTopicCreateDTO(Topic topic);
    public Topic topicCreateDTOToTopic(TopicCreateDTO topicCreateDTO);

    //TopicUpdate
    public Topic topicUpdateDTOtoTopic(TopicUpdateDTO topicUpdateDTO);
}
