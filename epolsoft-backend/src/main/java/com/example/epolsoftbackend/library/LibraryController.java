package com.example.epolsoftbackend.library;

import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@CrossOrigin
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
