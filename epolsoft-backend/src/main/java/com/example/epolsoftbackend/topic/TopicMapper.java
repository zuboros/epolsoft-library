package com.example.epolsoftbackend.topic;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    public TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    public TopicDTO topicToTopicDTO(Topic topic);
    public Topic topicDTOToTopic(TopicDTO topicDTO);
    public List<TopicDTO> listTopicToListTopicDTO(List<Topic> topicList);
}
