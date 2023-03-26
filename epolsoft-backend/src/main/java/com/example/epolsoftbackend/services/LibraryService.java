package com.example.epolsoftbackend.services;

import org.springframework.stereotype.Service;
import com.example.epolsoftbackend.repositories.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class LibraryService {
    private final Path fileStorageLocation = Path.of(System.getProperty("user.home") + "/bookCollection");
    
    @Autowired
    private BookRepository bookRepository;
    
    public String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Files.copy(file.getInputStream(),
                Path.of(fileStorageLocation.toString() + "/" + fileName),
                StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
    
    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        
        if(resource.exists()) {
            return resource;
        } else {
            return null;
        }
    }
    
    public boolean deleteFile(String fileName) throws IOException {
        Files.deleteIfExists(Path.of(fileStorageLocation + "/" + fileName));
        
        return Files.exists(Path.of(fileStorageLocation + "/" + fileName));
    }
}
