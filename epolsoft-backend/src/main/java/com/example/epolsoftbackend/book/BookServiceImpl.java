package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.book.DTO.BookCreateDTO;
import com.example.epolsoftbackend.book.DTO.BookUpdateDTO;
import com.example.epolsoftbackend.exception.BadRequestException;
import com.example.epolsoftbackend.exception.ForbiddenException;
import com.example.epolsoftbackend.exception.ResourceNotFoundException;
import com.example.epolsoftbackend.topic.Topic;
import com.example.epolsoftbackend.topic.TopicRepository;
import com.example.epolsoftbackend.topic.TopicService;
import com.example.epolsoftbackend.user.UserDetailsImpl;
import com.example.epolsoftbackend.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

    public BookCreateDTO create(BookCreateDTO bookCreateDTO) {
        try {
            Book createBook = bookMapper.bookCreateDTOToBook(bookCreateDTO);

            Optional<Topic> optTopic = topicRepository.findById(createBook.getTopicId().getId());
            if (optTopic.isPresent()) {
                Topic topic = optTopic.get();
                topic.setActive(true);
                topicRepository.save(topic);

                createBook.setTopicId(topicService.findById(createBook.getTopicId().getId()).orElse(null));
                createBook.setUserId(userService.findById(createBook.getUserId().getId()).orElse(null));

                return bookMapper.bookToBookCreateDTO(bookRepository.saveAndFlush(createBook));
            }
            else throw new ResourceNotFoundException("Topic", "id", bookCreateDTO.getTopicId());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new InternalError("Cant create book, name book: " + bookCreateDTO.getName());
        }
    }

    public Optional<Book> findById(long id){
        return bookRepository.findById(id);
    }

    public BookUpdateDTO updateById(BookUpdateDTO bookUpdateDTO) {
        try{
            Book oldBook = bookRepository.findById(bookUpdateDTO.getId()).get();

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!Objects.equals(oldBook.getUserId().getId(), userDetails.getId())) {
                throw new ForbiddenException("Can't update not own book");
            }

            Topic oldTopic = topicRepository.findById(oldBook.getTopicId().getId()).get();

            Book updateBook = bookMapper.bookUpdateDTOToBook(bookUpdateDTO);

            Topic newTopic = topicRepository.findById(updateBook.getTopicId().getId()).get();

            updateBook.setTopicId(topicService.findById(updateBook.getTopicId().getId()).orElse(null));
            updateBook.setUserId(userService.findById(updateBook.getUserId().getId()).orElse(null));

            oldTopic.setActive(oldTopic.getBooks().size() != 0);

            topicRepository.save(oldTopic);

            newTopic.setActive(newTopic.getBooks().size() != 0);

            topicRepository.save(newTopic);

            return bookMapper.bookToBookUpdateDTO(bookRepository.saveAndFlush(updateBook));
        }
       catch (Exception e ) {
            e.printStackTrace();
           throw new ResourceNotFoundException("Book", "id", bookUpdateDTO.getId());
        }
    }

    public boolean deleteById(Long id) {
        try {
            Optional<Book> optBook = bookRepository.findById(id);
            if (optBook.isPresent()) {
                Book book = optBook.get();

                UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if(!Objects.equals(book.getUserId().getId(), userDetails.getId())) {
                    throw new ForbiddenException("Can't update not own book");
                }

                long topicId = book.getTopicId().getId();

                bookRepository.deleteById(id);

                Optional<Topic> optTopic = topicRepository.findById(topicId);

                if (optTopic.isPresent()) {
                    Topic topic = optTopic.get();
                    if (topic.getBooks().size() == 0) {
                        topic.setActive(false);
                        topicRepository.save(topic);
                    }
                    return true;
                }
                throw new ResourceNotFoundException("Topic", "id", topicId);
            } else throw new BadRequestException("book is not exist");
        } catch (Exception e) {
            throw new ResourceNotFoundException("Book", "id", id);
        }
    }
}
