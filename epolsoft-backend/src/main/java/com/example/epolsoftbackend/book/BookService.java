package com.example.epolsoftbackend.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public ResponseEntity<BookDTO> create(BookDTO bookDTO){
        try {
            return new ResponseEntity<>(bookMapper.bookToBookDTO(bookRepository.saveAndFlush(bookMapper.bookDTOToBook(bookDTO))), HttpStatus.CREATED);
        } catch (Exception e) {return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
    }

    public Optional<Book> findById(long id){
        return bookRepository.findById(id);
    }

    public ResponseEntity<BookDTO> updateById(BookDTO bookDTO){
        try{
            return new ResponseEntity<>(bookMapper.bookToBookDTO(bookRepository
                    .saveAndFlush(bookMapper.bookDTOToBook(bookDTO))),HttpStatus.OK);
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
