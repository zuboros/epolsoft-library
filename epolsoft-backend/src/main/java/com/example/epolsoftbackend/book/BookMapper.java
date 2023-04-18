package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.book.DTO.BookCreateDTO;
import com.example.epolsoftbackend.book.DTO.BookDetailedDTO;
import com.example.epolsoftbackend.book.DTO.BookUpdateDTO;
import com.example.epolsoftbackend.topic.Topic;
import com.example.epolsoftbackend.user.User;
import com.example.epolsoftbackend.user.UserMapper;
import com.example.epolsoftbackend.topic.TopicMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, TopicMapper.class}, componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

     default BookCreateDTO bookToBookCreateDTO(Book book){
        if ( book == null ) {
            return null;
        }

        BookCreateDTO.BookCreateDTOBuilder bookCreateDTO = BookCreateDTO.builder();

        bookCreateDTO.userId( book.getUserId().getId() );
        bookCreateDTO.topicId( book.getTopicId().getId() );
        bookCreateDTO.name( book.getName() );
        bookCreateDTO.description( book.getDescription() );
        bookCreateDTO.shortDescription( book.getShortDescription() );
        bookCreateDTO.fileName( book.getFileName() );
        bookCreateDTO.filePath( book.getFilePath() );

        return bookCreateDTO.build();
    }

    default Book bookCreateDTOToBook(BookCreateDTO bookCreateDTO) {
        if ( bookCreateDTO == null ) {
            return null;
        }

        Book.BookBuilder book = Book.builder();

        User user = new User();
        user.setId(bookCreateDTO.getUserId());
        book.userId( user );

        Topic topic = new Topic();
        topic.setId(bookCreateDTO.getTopicId());
        book.topicId( topic );

        book.name( bookCreateDTO.getName() );
        book.shortDescription( bookCreateDTO.getShortDescription() );
        book.description( bookCreateDTO.getDescription() );
        book.fileName( bookCreateDTO.getFileName() );
        book.filePath( bookCreateDTO.getFilePath() );

        return book.build();
    }

    default BookUpdateDTO bookToBookUpdateDTO(Book book) {
        if ( book == null ) {
        return null;
        }

        BookUpdateDTO.BookUpdateDTOBuilder bookUpdateDTO = BookUpdateDTO.builder();

        bookUpdateDTO.userId( book.getUserId().getId() );
        bookUpdateDTO.topicId( book.getTopicId().getId() );
        bookUpdateDTO.id( book.getId() );
        bookUpdateDTO.name( book.getName() );
        bookUpdateDTO.description( book.getDescription() );
        bookUpdateDTO.shortDescription( book.getShortDescription() );
        bookUpdateDTO.fileName( book.getFileName() );
        bookUpdateDTO.filePath( book.getFilePath() );

        return bookUpdateDTO.build();
    }

    default Book bookUpdateDTOToBook(BookUpdateDTO bookCreateDTO){
        if ( bookCreateDTO == null ) {
            return null;
        }

        Book.BookBuilder book = Book.builder();

        User user = new User();
        user.setId(bookCreateDTO.getUserId());
        book.userId( user );

        Topic topic = new Topic();
        topic.setId(bookCreateDTO.getTopicId());
        book.topicId( topic );

        book.userId( user );
        book.topicId( topic );
        book.id( bookCreateDTO.getId() );
        book.name( bookCreateDTO.getName() );
        book.shortDescription( bookCreateDTO.getShortDescription() );
        book.description( bookCreateDTO.getDescription() );
        book.fileName( bookCreateDTO.getFileName() );
        book.filePath( bookCreateDTO.getFilePath() );

        return book.build();
    }

    default BookDetailedDTO bookToBookDetailedDTO(Book book) {
         if ( book == null ) {
             return null;
         }

         BookDetailedDTO.BookDetailedDTOBuilder bookDetailedDTOBuilder = BookDetailedDTO.builder();

         bookDetailedDTOBuilder.user( book.getUserId().getName() );
         bookDetailedDTOBuilder.name( book.getName() );
         bookDetailedDTOBuilder.id( book.getId() );
         bookDetailedDTOBuilder.topic( book.getTopicId().getName() );
         bookDetailedDTOBuilder.description( book.getDescription() );
         bookDetailedDTOBuilder.shortDescription( book.getShortDescription() );
         bookDetailedDTOBuilder.createdAt( book.getCreatedAt() );
         bookDetailedDTOBuilder.updatedAt( book.getUpdatedAt() );

         return bookDetailedDTOBuilder.build();
    }
}
