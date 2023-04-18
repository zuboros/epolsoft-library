package com.example.epolsoftbackend.book;

import com.example.epolsoftbackend.book.DTO.BookCreateDTO;
import com.example.epolsoftbackend.book.DTO.BookDetailedDTO;
import com.example.epolsoftbackend.book.DTO.BookUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@CrossOrigin
@RequiredArgsConstructor
public class BookController {
    final BookService bookService;

    @PostMapping()
    public ResponseEntity<BookCreateDTO> createNote(@RequestBody BookCreateDTO bookCreateDTO){
        return new ResponseEntity<>(bookService.create(bookCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDetailedDTO> selectBook(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.selectBook(id), HttpStatus.OK);
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

    @PostMapping("/{id}")
    public ResponseEntity<BookUpdateDTO> setStatus(@PathVariable Long id,@RequestParam String status) {
        return new ResponseEntity<>(bookService.setStatus(id, status), HttpStatus.OK);
    }
}
