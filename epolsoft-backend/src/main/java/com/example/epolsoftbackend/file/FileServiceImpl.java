package com.example.epolsoftbackend.file;

import java.io.File;

import com.example.epolsoftbackend.book.Book;
import com.example.epolsoftbackend.book.BookRepository;
import com.example.epolsoftbackend.exception.ForbiddenException;
import com.example.epolsoftbackend.exception.ResourceNotFoundException;
import com.example.epolsoftbackend.user.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import org.springframework.util.StringUtils;

@Service
public class FileServiceImpl implements FileService {

    private final Path fileStorageLocation = Path.of(
            System.getProperty("user.dir") + File.separator + "bookCollection");
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public FileServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }
    
    public String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty or null");
        }
        
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String dirTodayPathStr = formatter.format(date);
        String[] dirTodayPathStrArray = dirTodayPathStr.split("/");
        
        Path dirTodayPath = Paths.get(fileStorageLocation + File.separator
                + dirTodayPathStrArray[0] + File.separator
                + dirTodayPathStrArray[1] + File.separator
                + dirTodayPathStrArray[2]);

        try {
            Files.createDirectories(dirTodayPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create new directories");
        }

        String fileUUIDName = UUID.randomUUID().toString();

        try {
            Files.copy(file.getInputStream(),
                    Path.of(dirTodayPath + File.separator + fileUUIDName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create a new file");
        }

        return dirTodayPathStr + "/" + fileUUIDName;
    }
    
    public Resource loadFileAsResource(long id, String type, long userWhoDownloadId) {
        if (type.equals("book")) {
            Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

            if (!Objects.equals(book.getUserId().getId(), userWhoDownloadId)) {
                throw new ForbiddenException("User cannot download not his own books");
            }
        }

        String filePathStr = type.equals("book") ?
                bookRepository.findById(id).get().getFilePath() : userRepository.findById(id).get().getAvatarPath();
        String resourceName = type.equals("book") ?
                bookRepository.findById(id).get().getFileName() : userRepository.findById(id).get().getAvatarName();

        if (filePathStr == null) {
            throw new ResourceNotFoundException("File", "filePathStr or resourceName", null);
        }

        if (filePathStr.isBlank() || !filePathStr.contains("/")) {
            throw new RuntimeException("Incorrect file path stored in DB");
        }
        
        String[] filePathStrArray = filePathStr.split("/");
        
        String dirPathStr = filePathStrArray[0] + File.separator
                + filePathStrArray[1] + File.separator
                + filePathStrArray[2];
        
        String fileName = filePathStrArray[filePathStrArray.length - 1];
        
        Path newFileStorageLocation = Path.of(this.fileStorageLocation
                + File.separator + dirPathStr);

        Path filePath = newFileStorageLocation.resolve(fileName).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Malformed file URL");
        }

        if(resource.exists()) {
            return resource;
        } else {
            throw new ResourceNotFoundException("Resource", "filePathStr", filePathStr);
        }
    }

    public String receiveFileName(long id, String type) {
        if (type.equals("book")) {
            return bookRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Book", "id", id)).getFileName();
        } else {
            return userRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", id)).getAvatarName();
        }
    }
    
    public boolean deleteFile(long id, long userWhoDeleteId) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

        if (!Objects.equals(book.getUserId().getId(), userWhoDeleteId)) {
            throw new ForbiddenException("User cannot delete not his own books");
        }

        String filePathStr = book.getFilePath();

        if (filePathStr == null
                || filePathStr.isBlank()
                || !filePathStr.contains("/")) {
            throw new RuntimeException("Incorrect file path stored in DB");
        }
        
        String[] filePathStrArray = filePathStr.split("/");
        
        String dirPathStr = filePathStrArray[0] + File.separator
                + filePathStrArray[1] + File.separator
                + filePathStrArray[2];
        
        String fileName = filePathStrArray[filePathStrArray.length - 1];
        
        Path newFileStorageLocation = Path.of(this.fileStorageLocation 
                + File.separator + dirPathStr);

        try {
            Files.deleteIfExists(Path.of(newFileStorageLocation
                    + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException occurred while file is deleting");
        }

        return Files.exists(Path.of(newFileStorageLocation
                + File.separator + fileName));
    }
}
