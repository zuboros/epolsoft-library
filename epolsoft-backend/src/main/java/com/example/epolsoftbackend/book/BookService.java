package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.book.DTO.BookCreateDTO;
import com.example.epolsoftbackend.book.DTO.BookUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface BookService {

    ResponseEntity<BookCreateDTO> create(BookCreateDTO bookCreateDTO);
    Optional<Book> findById(long id);
    ResponseEntity<BookUpdateDTO> updateById(BookUpdateDTO bookUpdateDTO);
    ResponseEntity<HttpStatus> deleteById(long id);

}
