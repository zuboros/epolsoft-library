package com.example.epolsoftbackend.services;

import com.example.epolsoftbackend.entities.Book;
import com.example.epolsoftbackend.repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import payload.BookModel;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service

public class BookService {

        private final BookRepository repository;

        public BookService(BookRepository repository) {
            this.repository = repository;
        }

        public Book create(BookModel book) {
            var bookToSave = Book.builder()
                    .name(book.getName())
                    .topicId(book.getTopic_id())
                    .userId(book.getUser_id())
                    .description(book.getDescription())
                    .shortDescription(book.getShortDescription())
                    .file(book.getFile())
                    .build();

            return repository.save(bookToSave);
        }

        public List<Book> getBooksSortedBy(String sortField) {
        switch (sortField) {
            case "name":
                return repository.findAllByOrderByName();
            case "topic_id":
                return repository.findAllByOrderByTopic();
            case "user_id":
                return repository.findAllByOrderByUser();
            case "description":
                return repository.findAllByOrderByDescription();
            case "short_description":
                return repository.findAllByOrderByShortDescription();
            case "updated_at":
                return repository.findAllByOrderByUpdatedAt();
            case "created_at":
                return repository.findAllByOrderByCreatedAt();
            default:
                return repository.findAll();
        }
    }

        public Page<Book> getBooks(int pageNumber, int pageSize) {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            return repository.findAll(pageable);
        }
        public void updateOne(long id, BookModel book) {
            if (repository.findById(id).isEmpty()) throw new EntityNotFoundException();
            repository.updateById(book.getName(), book.getTopic_id(), book.getUser_id(),
                    book.getDescription(), book.getShortDescription(), book.getFile(), id);
        }

        public void deleteById(long id) {
            repository.deleteById(id);
        }

        public void deleteAll() {
            repository.deleteAll();
        }
}
