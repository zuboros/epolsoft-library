package com.example.epolsoftbackend.library;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/library")
public class LibraryController {
    final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping()
    public ResponseEntity<List> getBooks(Pageable pageable){
        return new ResponseEntity<>(libraryService.findByCriteria(pageable), HttpStatus.OK);
    }
}
