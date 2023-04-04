package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.author.AuthorMapper;
import com.example.epolsoftbackend.topic.TopicMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AuthorMapper.class, TopicMapper.class}, componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    @Mapping(source = "authorId", target = "authorDTO")
    @Mapping(source = "topicId", target = "topicDTO")
    public BookDTO bookToBookDTO(Book book);
    @Mapping(source = "authorDTO", target = "authorId")
    @Mapping(source = "topicDTO", target = "topicId")
    public Book bookDTOToBook(BookDTO bookDTO);
}
