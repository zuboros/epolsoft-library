package com.example.epolsoftbackend.controllers;

import com.example.epolsoftbackend.entities.Author;
import com.example.epolsoftbackend.entities.Book;
import com.example.epolsoftbackend.entities.Library;
import com.example.epolsoftbackend.entities.Topic;
import com.example.epolsoftbackend.payload.NoteModel;
import com.example.epolsoftbackend.services.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/library")
public class LibraryController {
    final FileService fileService;
    final BookService bookService;
    final AuthorService authorService;
    final TopicService topicService;
    final LibraryService libraryService;

    public LibraryController(FileService fileService, BookService bookService, AuthorService authorService, TopicService topicService, LibraryService libraryService) {
        this.fileService = fileService;
        this.bookService = bookService;
        this.authorService = authorService;
        this.topicService = topicService;
        this.libraryService = libraryService;
    }


    @PostMapping("/uploadFile")
    @ResponseStatus(HttpStatus.OK)
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.storeFile(file);
    }
    
    @PostMapping("/deleteFile")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFile(@RequestParam("fileName") String fileName) throws IOException {
        fileService.deleteFile(fileName);
    }
    
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws MalformedURLException {
        // Load file as Resource
        Resource resource = fileService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    //CRUD data operations
    @PostMapping()
    public ResponseEntity<Book> createNote(@RequestBody NoteModel newNote){
        var topic = topicService.searchExistingOrCreateNew(newNote.getTopicId(), newNote.getTopicName());
        var newAuthor = authorService.create(newNote.getAuthorName());
        return bookService.create(newNote, newAuthor, topic);
    }

    @GetMapping()
    public ResponseEntity<List<Library>> getBooks(@RequestParam(value = "numberPage", defaultValue = "1") int numberPage,
                                                  @RequestParam(value = "sortingOrder") String sortingOrder,
                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                  @RequestParam(value = "sortingField", defaultValue = "name") String sortingField){
        Pageable pageable = PageRequest.of(numberPage - 1, size);
        return new ResponseEntity<>(libraryService.findByCriteria(null, sortingOrder, sortingField,
                pageable), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateOneBook(@PathVariable("id") long id, @RequestBody NoteModel updateNote) {
        Author author = authorService.searchExistingOrCreateNew(updateNote.getAuthorId(), updateNote.getAuthorName());
        Topic topic = topicService.searchExistingOrCreateNew(updateNote.getTopicId(), updateNote.getTopicName());
        return bookService.updateById(id, updateNote, author, topic);
    }

    @GetMapping("/get/authors")
    public ResponseEntity<List<Author>> getAllAuthors(){
        return authorService.getAll();
    }
    @GetMapping("/get/topics")
    public ResponseEntity<List<Topic>> getAllTopics(){
        return topicService.getAll();
    }

    @DeleteMapping("/deleteNote/{id}")
    public ResponseEntity<HttpStatus> deleteNote(@PathVariable("id") long id) {
            return bookService.deleteById(id);
    }

}
