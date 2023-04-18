package com.example.epolsoftbackend.library;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {
    final LibraryService libraryService;

    @GetMapping()
    public ResponseEntity<List> getBooks(Pageable pageable, LibrarySearchModel librarySearchModel){
        return new ResponseEntity<>(libraryService.findByCriteria(pageable, librarySearchModel), HttpStatus.OK);
    }

    @GetMapping("/on_approving")
    public ResponseEntity<List> getBooksForModerator(Pageable pageable){
        return new ResponseEntity<>(libraryService.getBooksForModerator(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List> getBooksUser(@PathVariable("id") long id, Pageable pageable) {
        return new ResponseEntity<>(libraryService.getBooksUser(id, pageable), HttpStatus.OK);
    }

}
