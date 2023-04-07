package com.example.epolsoftbackend.file;

import com.example.epolsoftbackend.book.Book;
import com.example.epolsoftbackend.book.BookService;
import com.example.epolsoftbackend.user.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

@RestController
@RequestMapping("/api/file")
public class FileController {

    final FileService fileService;
    final BookService bookService;
    final UserService userService;

    public FileController(FileService fileService, BookService bookService, UserService userService) {
        this.fileService = fileService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileService.storeFile(file), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFile(@PathVariable("id") long id) throws IOException {
        Optional<Book> optionalBook = bookService.findById(id);
        Book book = optionalBook.isEmpty() ? null : optionalBook.get();

        if (book == null) {
            return;
        }

        String filePath = book.getFilePath();

        fileService.deleteFile(filePath);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable long id, @RequestParam("type") String type,
                                                 HttpServletRequest request) throws MalformedURLException {

        String resourcePath = type.equals("book") ?
                bookService.findById(id).get().getFilePath() : userService.findById(id).get().getAvatarPath();
        String resourceName = type.equals("book") ?
                bookService.findById(id).get().getFileName() : userService.findById(id).get().getAvatarName();

        if (resourcePath == null || resourceName == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Load file as Resource
        Resource resource = fileService.loadFileAsResource(resourcePath);

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
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resourceName + "\"")
                .body(resource);
    }

}
