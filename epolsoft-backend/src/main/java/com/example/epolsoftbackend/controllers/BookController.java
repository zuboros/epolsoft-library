package com.example.epolsoftbackend.controllers;

import com.example.epolsoftbackend.entities.Book;
import com.example.epolsoftbackend.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payload.BookModel;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    BookService BookService;

    public BookController(BookService BookService) {this.BookService = BookService;}

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOneBook(@PathVariable("id") int id, @RequestBody BookModel book) {
        BookService.updateOne(id, book);
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getBooks(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "15") int size) {
        Page<Book> books = BookService.getBooks(page, size);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/sortedBy")
    public List<Book> getBooksSortedBy(@RequestParam("sortField") String sortField) {
        return BookService.getBooksSortedBy(sortField);
    }

        @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneBook(@PathVariable("id") int id) {
        BookService.deleteById(id);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllBooks() { BookService.deleteAll(); }
}