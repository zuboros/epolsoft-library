package com.example.epolsoftbackend.services;

import com.example.epolsoftbackend.entities.Author;
import com.example.epolsoftbackend.entities.Book;
import com.example.epolsoftbackend.entities.Topic;
import com.example.epolsoftbackend.payload.NoteModel;
import com.example.epolsoftbackend.repositories.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ResponseEntity<Book> create(NoteModel noteModel, Author author, Topic topic) {
        var bookToSave = Book.builder()
                .name(noteModel.getBookName())
                .file(noteModel.getBookFileName())
                .shortDescription(noteModel.getBookShortDescription())
                .description(noteModel.getBookDescription())
                .authorId(author)
                .topicId(topic)
                .build();

        return new ResponseEntity<>(bookRepository.saveAndFlush(bookToSave), HttpStatus.CREATED);
    }

    public Optional<Book> findById(long id){
        return bookRepository.findById(id);
    }

    public ResponseEntity<Book> updateById(long id, NoteModel updateNote, Author author, Topic topic) {
        Optional<Book> optionalBook = this.findById(id);
        if(optionalBook.isPresent()) {
            Book editableBook = optionalBook.get();
            editableBook.setName(updateNote.getBookName());
            editableBook.setFile(updateNote.getBookFileName());
            editableBook.setDescription(updateNote.getBookDescription());
            editableBook.setShortDescription(updateNote.getBookShortDescription());
            editableBook.setTopicId(topic);
            editableBook.setAuthorId(author);
            return new ResponseEntity<>(bookRepository.saveAndFlush(editableBook), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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