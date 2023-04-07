package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.book.DTO.BookCreateDTO;
import com.example.epolsoftbackend.book.DTO.BookUpdateDTO;
import com.example.epolsoftbackend.topic.Topic;
import com.example.epolsoftbackend.topic.TopicRepository;
import com.example.epolsoftbackend.topic.TopicService;
import com.example.epolsoftbackend.topic.TopicServiceImpl;
import com.example.epolsoftbackend.user.UserService;
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
    private final TopicRepository topicRepository;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper, UserService userService, TopicService topicService, TopicRepository topicRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.userService = userService;
        this.topicService = topicService;
        this.topicRepository = topicRepository;
    }

    public ResponseEntity<BookCreateDTO> create(BookCreateDTO bookCreateDTO) {
        try {
            Book createBook = bookMapper.bookCreateDTOToBook(bookCreateDTO);

            Topic topic = topicRepository.findById(createBook.getTopicId().getId()).get();
            topic.setActive(true);
            topicRepository.save(topic);

            createBook.setTopicId(topicService.findById(createBook.getTopicId().getId()).orElse(null));
            createBook.setUserId(userService.findById(createBook.getUserId().getId()).orElse(null));

            ResponseEntity<BookCreateDTO> responseEntity = new ResponseEntity<>(bookMapper.bookToBookCreateDTO(bookRepository.saveAndFlush(createBook)), HttpStatus.CREATED);

            return responseEntity;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public Optional<Book> findById(long id){
        return bookRepository.findById(id);
    }

    public ResponseEntity<BookUpdateDTO> updateById(BookUpdateDTO bookUpdateDTO) {
        try{
            Book oldBook = bookRepository.findById(bookUpdateDTO.getId()).get();

            Topic oldTopic = topicRepository.findById(oldBook.getTopicId().getId()).get();

            Book updateBook = bookMapper.bookUpdateDTOToBook(bookUpdateDTO);

            Topic newTopic = topicRepository.findById(updateBook.getTopicId().getId()).get();

            updateBook.setTopicId(topicService.findById(updateBook.getTopicId().getId()).orElse(null));
            updateBook.setUserId(userService.findById(updateBook.getUserId().getId()).orElse(null));

            ResponseEntity<BookUpdateDTO> responseEntity = new ResponseEntity<>(bookMapper.bookToBookUpdateDTO(bookRepository
                    .saveAndFlush(updateBook)),HttpStatus.OK);

            if (oldTopic.getBooks().size() == 0) {
                oldTopic.setActive(false);
            } else {
                oldTopic.setActive(true);
            }

            topicRepository.save(oldTopic);

            if (newTopic.getBooks().size() == 0) {
                newTopic.setActive(false);
            } else {
                newTopic.setActive(true);
            }

            topicRepository.save(newTopic);

            return responseEntity;
        }
       catch (Exception e ) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> deleteById(long id) {
        try {
            Book book = bookRepository.findById(id).get();

            long topicId = book.getTopicId().getId();

            bookRepository.deleteById(id);

            Topic topic = topicRepository.findById(topicId).get();

            if (topic.getBooks().size() == 0) {
                topic.setActive(false);
                topicRepository.save(topic);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
