package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.book.DTO.BookCreateDTO;
import com.example.epolsoftbackend.book.DTO.BookUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookController {
    final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping()
    public ResponseEntity<BookCreateDTO> createNote(@RequestBody BookCreateDTO bookCreateDTO){
        return bookService.create(bookCreateDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<BookUpdateDTO> updateOneBook(@RequestBody BookUpdateDTO bookUpdateDTO, Long idUser) {
        return bookService.updateById(bookUpdateDTO, idUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteNote(@PathVariable("id") Long id, Long idUser) {
        return bookService.deleteById(id, idUser);
    }
}
