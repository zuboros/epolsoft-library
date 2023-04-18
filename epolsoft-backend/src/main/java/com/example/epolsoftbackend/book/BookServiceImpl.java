package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.book.DTO.BookCreateDTO;
import com.example.epolsoftbackend.book.DTO.BookDetailedDTO;
import com.example.epolsoftbackend.book.DTO.BookUpdateDTO;
import com.example.epolsoftbackend.exception.BadRequestException;
import com.example.epolsoftbackend.exception.ForbiddenException;
import com.example.epolsoftbackend.exception.InternalServerErrorException;
import com.example.epolsoftbackend.exception.ResourceNotFoundException;
import com.example.epolsoftbackend.file.FileService;
import com.example.epolsoftbackend.topic.Topic;
import com.example.epolsoftbackend.topic.TopicRepository;
import com.example.epolsoftbackend.topic.TopicService;
import com.example.epolsoftbackend.user.UserDetailsImpl;
import com.example.epolsoftbackend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserService userService;
    private final TopicService topicService;
    private final TopicRepository topicRepository;
    private final FileService fileService;

    public BookCreateDTO create(BookCreateDTO bookCreateDTO) {
        try {
            Book createBook = bookMapper.bookCreateDTOToBook(bookCreateDTO);

            Topic topic = topicRepository.findById(createBook.getTopicId().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("Topic", "id", bookCreateDTO.getTopicId()));

            topic.setActive(true);
            topicRepository.save(topic);

            createBook.setStatus("CREATED");
            createBook.setTopicId(topicService.findById(createBook.getTopicId().getId()).orElse(null));
            createBook.setUserId(userService.findById(createBook.getUserId().getId()).orElse(null));

            return bookMapper.bookToBookCreateDTO(bookRepository.saveAndFlush(createBook));

        } catch (Exception e) {
            throw new InternalServerErrorException("Cant create book, name book: " + bookCreateDTO.getName());
        }
    }

    public Optional<Book> findById(long id){
        return bookRepository.findById(id);
    }

    public BookUpdateDTO updateById(BookUpdateDTO bookUpdateDTO) {
            Book oldBook = bookRepository.findById(bookUpdateDTO.getId()).orElseThrow(
                    () -> new ResourceNotFoundException("OldBook", "id", bookUpdateDTO.getId()));

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!Objects.equals(oldBook.getUserId().getId(), userDetails.getId()) || oldBook.getStatus().equals("ARCHIVED")) {
                throw new ForbiddenException("Can't update not own book or archived book");
            }

            fileService.deleteBookFile(oldBook.getId());

            Topic oldTopic = topicRepository.findById(oldBook.getTopicId().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("OldTopic", "id", oldBook.getTopicId().getId()));

            Book updateBook = bookMapper.bookUpdateDTOToBook(bookUpdateDTO);

            Topic newTopic = topicRepository.findById(updateBook.getTopicId().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("NewTopic", "id", updateBook.getTopicId().getId()));

            updateBook.setTopicId(topicService.findById(updateBook.getTopicId().getId()).orElse(null));
            updateBook.setUserId(userService.findById(updateBook.getUserId().getId()).orElse(null));
            updateBook.setCreatedAt(oldBook.getCreatedAt());
            updateBook.setStatus("WAIT_APPROVING");

            if(updateBook.getFileName() == null && updateBook.getFileName() == null) {
                updateBook.setFileName(oldBook.getFileName());
                updateBook.setFilePath(oldBook.getFilePath());
            }

            oldTopic.setActive(oldTopic.getBooks().size() != 0);

            topicRepository.save(oldTopic);

            newTopic.setActive(newTopic.getBooks().size() != 0);

            topicRepository.save(newTopic);

            return bookMapper.bookToBookUpdateDTO(bookRepository.saveAndFlush(updateBook));

    }

    public BookUpdateDTO setStatus(long id, String status){
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("OldBook", "id", id));

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        switch(status) {
            case "WAIT_APPROVING":
                if(!Objects.equals(book.getUserId().getId(), userDetails.getId())) {
                    throw new ForbiddenException("Can't update status not own book");
                }

                if(Objects.equals(book.getStatus(), "CREATED")) {
                    book.setStatus(status);
                    return bookMapper.bookToBookUpdateDTO(bookRepository.save(book));
                }
                else throw new InternalServerErrorException("Saving entity ending with fail");
            case "ARCHIVED":
                if(!Objects.equals(book.getUserId().getId(), userDetails.getId())) {
                    throw new ForbiddenException("Can't update status not own book");
                }

                if(Objects.equals(book.getStatus(), "ACTIVED")) {
                    book.setStatus(status);
                    return bookMapper.bookToBookUpdateDTO(bookRepository.save(book));
                }
                else throw new InternalServerErrorException("Saving entity ending with fail");

            case "BLOCKED":
            case "ACTIVED":
                if((userDetails.getAuthorities().stream().anyMatch(
                        grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MODERATOR")) )) {
                    throw new ForbiddenException("Can't update status without Moderator role");
                }

                if(Objects.equals(book.getStatus(), "WAIT_APPROVING")) {
                    book.setStatus(status);
                    return bookMapper.bookToBookUpdateDTO(bookRepository.save(book));
                }
                else throw new InternalServerErrorException("Saving entity ending with fail");

            default:
                throw new BadRequestException("Error in status");
        }
    }

    public void deleteById(Long id) {
            Book book = bookRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Book", "id", id));

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!Objects.equals(book.getUserId().getId(), userDetails.getId())) {
                throw new ForbiddenException("Can't update not own book");
            }

            long topicId = book.getTopicId().getId();

            bookRepository.deleteById(id);

            Topic topic = topicRepository.findById(topicId).orElseThrow(
                    () -> new ResourceNotFoundException("Topic", "id", topicId));

            if (topic.getBooks().size() == 0) {
                topic.setActive(false);
                topicRepository.save(topic);
            }
    }

    public BookDetailedDTO selectBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book", "id", id));
        return bookMapper.bookToBookDetailedDTO(book);
    }

}
