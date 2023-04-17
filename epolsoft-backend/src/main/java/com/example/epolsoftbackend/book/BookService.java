package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.book.DTO.BookCreateDTO;
import com.example.epolsoftbackend.book.DTO.BookDetailedDTO;
import com.example.epolsoftbackend.book.DTO.BookUpdateDTO;
import java.util.Optional;

public interface BookService {

    BookCreateDTO create(BookCreateDTO bookCreateDTO);
    Optional<Book> findById(long id);
    BookUpdateDTO updateById(BookUpdateDTO bookUpdateDTO);
    void deleteById(Long id);
    BookDetailedDTO selectBook(Long id);
    BookUpdateDTO setStatus(long id, String status);

}
