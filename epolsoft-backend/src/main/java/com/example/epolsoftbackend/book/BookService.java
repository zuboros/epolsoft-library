package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.book.DTO.BookCreateDTO;
import com.example.epolsoftbackend.book.DTO.BookUpdateDTO;
import java.util.Optional;

public interface BookService {

    BookCreateDTO create(BookCreateDTO bookCreateDTO);
    Optional<Book> findById(long id);
    BookUpdateDTO updateById(BookUpdateDTO bookUpdateDTO);
    boolean deleteById(Long id);

}
