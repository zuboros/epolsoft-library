package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.book.DTO.BookCreateDTO;
import com.example.epolsoftbackend.book.DTO.BookUpdateDTO;
import com.example.epolsoftbackend.user.UserMapper;
import com.example.epolsoftbackend.topic.TopicMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, TopicMapper.class}, componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    @Mapping(source = "userId", target = "userResponseDTO")
    @Mapping(source = "topicId", target = "topicResponseDTO")
    public BookCreateDTO bookToBookCreateDTO(Book book);
    @Mapping(source = "userResponseDTO", target = "userId")
    @Mapping(source = "topicResponseDTO", target = "topicId")
    public Book bookCreateDTOToBook(BookCreateDTO bookCreateDTO);

    @Mapping(source = "userId", target = "userResponseDTO")
    @Mapping(source = "topicId", target = "topicResponseDTO")
    public BookUpdateDTO bookToBookUpdateDTO(Book book);
    @Mapping(source = "userResponseDTO", target = "userId")
    @Mapping(source = "topicResponseDTO", target = "topicId")
    public Book bookUpdateDTOToBook(BookUpdateDTO bookCreateDTO);
}
