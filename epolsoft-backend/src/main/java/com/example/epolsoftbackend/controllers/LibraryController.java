package com.example.epolsoftbackend.controllers;

import com.example.epolsoftbackend.services.LibraryService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class LibraryController {
    @Autowired
    LibraryService LibraryService;

    public LibraryController(LibraryService LibraryService) {
        this.LibraryService = LibraryService;
    }
    
    @PostMapping("/uploadFile")
    @ResponseStatus(HttpStatus.OK)
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        LibraryService.storeFile(file);
    }
    
    @PostMapping("/deleteFile")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFile(@RequestParam("fileName") String fileName) throws IOException {
        LibraryService.deleteFile(fileName);
    }
}
