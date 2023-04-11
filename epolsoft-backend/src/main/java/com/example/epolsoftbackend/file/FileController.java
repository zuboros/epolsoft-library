package com.example.epolsoftbackend.file;

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
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("type") String type) {
        long userId = 3; //delete

        return new ResponseEntity<>(fileService.storeFile(file, type, userId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookFile(@PathVariable("id") long id) {
        long userWhoDeleteId = 3; //delete

        fileService.deleteBookFile(id, userWhoDeleteId);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable long id,
                                                 @RequestParam("type") String type,
                                                 HttpServletRequest request) {
        long userWhoDownloadId = 3;  //delete

        // Load file as Resource
        Resource resource = fileService.loadFileAsResource(id, type, userWhoDownloadId);
        String resourceName = fileService.receiveFileName(id, type);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new RuntimeException("Could not determine file type.");
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
