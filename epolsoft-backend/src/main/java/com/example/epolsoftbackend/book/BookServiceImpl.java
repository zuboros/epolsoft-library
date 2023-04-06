package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.book.DTO.BookCreateDTO;
import com.example.epolsoftbackend.book.DTO.BookUpdateDTO;
import com.example.epolsoftbackend.topic.TopicService;
import com.example.epolsoftbackend.topic.TopicServiceImpl;
import com.example.epolsoftbackend.user.UserService;
import com.example.epolsoftbackend.user.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserService userService;
    private final TopicService topicService;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper, UserService userService, TopicService topicService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.userService = userService;
        this.topicService = topicService;
    }

    public ResponseEntity<BookCreateDTO> create(BookCreateDTO bookCreateDTO) {
        try {
            Book createBook = bookMapper.bookCreateDTOToBook(bookCreateDTO);
            createBook.setTopicId(topicService.findById(createBook.getTopicId().getId()).orElse(null));
            createBook.setUserId(userService.findById(createBook.getUserId().getId()).orElse(null));
            return new ResponseEntity<>(bookMapper.bookToBookCreateDTO(bookRepository.saveAndFlush(createBook)), HttpStatus.CREATED);
        } catch (Exception e) {return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
    }

    public Optional<Book> findById(long id){
        return bookRepository.findById(id);
    }

    public ResponseEntity<BookUpdateDTO> updateById(BookUpdateDTO bookUpdateDTO) {
        try{
            Book updateBook = bookMapper.bookUpdateDTOToBook(bookUpdateDTO);
            updateBook.setTopicId(topicService.findById(updateBook.getTopicId().getId()).orElse(null));
            updateBook.setUserId(userService.findById(updateBook.getUserId().getId()).orElse(null));

            return new ResponseEntity<>(bookMapper.bookToBookUpdateDTO(bookRepository
                    .saveAndFlush(updateBook)),HttpStatus.OK);
        }
       catch (Exception e ) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
    }

    public ResponseEntity<HttpStatus> deleteById(long id) {
            try {
                bookRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
}
