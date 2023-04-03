package com.example.epolsoftbackend.book;

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
    public ResponseEntity<BookDTO> createNote(@RequestBody BookDTO bookDTO){
        return bookService.create(bookDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookDTO> updateOneBook(@RequestBody BookDTO bookDTO) {
        return bookService.updateById(bookDTO);
    }

    @DeleteMapping("/deleteNote/{id}")//delete file
    public ResponseEntity<HttpStatus> deleteNote(@PathVariable("id") long id) {
        return bookService.deleteById(id);
    }
}
