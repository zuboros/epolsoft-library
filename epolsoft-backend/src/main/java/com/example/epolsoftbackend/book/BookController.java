package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.book.DTO.BookCreateDTO;
import com.example.epolsoftbackend.book.DTO.BookUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@CrossOrigin
public class BookController {
    final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping()
    public ResponseEntity<BookCreateDTO> createNote(@RequestBody BookCreateDTO bookCreateDTO){
        return new ResponseEntity<>(bookService.create(bookCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<BookUpdateDTO> updateOneBook(@RequestBody BookUpdateDTO bookUpdateDTO) {
        return new ResponseEntity<>(bookService.updateById(bookUpdateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNote(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }
}
