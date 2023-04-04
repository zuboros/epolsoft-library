package com.example.epolsoftbackend.file;

import com.example.epolsoftbackend.book.Book;
import com.example.epolsoftbackend.book.BookService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.epolsoftbackend.file.FileService;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

@RestController
@RequestMapping("/api/file")
public class FileController {
    final FileService fileService;
    final BookService bookService;

    public FileController(FileService fileService, BookService bookService) {
        this.fileService = fileService;
        this.bookService = bookService;
    }

    @PostMapping("/uploadFile")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileService.storeFile(file), HttpStatus.OK);
    }

    @PostMapping("/deleteFile")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFile(@RequestParam("fileName") String fileName) throws IOException {
        fileService.deleteFile(fileName);
    }

    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable long id, HttpServletRequest request) throws MalformedURLException {

        Optional<Book> optionalBook = bookService.findById(id);
        Book book = optionalBook.isEmpty() ? null : optionalBook.get();

        if (book == null) {
            return null;
        }

        // Load file as Resource
        Resource resource = fileService.loadFileAsResource(book.getFilePath());

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
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + book.getFileName() + "\"")
                .body(resource);
    }
}
