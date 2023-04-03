package com.example.epolsoftbackend.file;

import java.io.File;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Service
public class FileService {
    private final Path fileStorageLocation = Path.of(System.getProperty("user.dir") + File.separator + "bookCollection");
    
    public String storeFile(MultipartFile file) throws IOException {
        //String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        if (file == null || file.isEmpty()) {
            return null;
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String dirTodayName = formatter.format(date);
        
        Files.createDirectories(Paths.get(fileStorageLocation + File.separator + dirTodayName));
        
        String fileUUIDName = UUID.randomUUID().toString();

        Files.copy(file.getInputStream(),
                Path.of(fileStorageLocation + File.separator
                        + dirTodayName + File.separator
                        + fileUUIDName),
                StandardCopyOption.REPLACE_EXISTING);
        
        return dirTodayName + "/" + fileUUIDName;
    }
    
    public Resource loadFileAsResource(String filePathStr) throws MalformedURLException {
        if (filePathStr == null || filePathStr.isBlank() || !filePathStr.contains("/")) {
            return null;
        }
        
        String dirName = filePathStr.split("/")[0];
        String fileName = filePathStr.split("/")[1];
        
        Path newFileStorageLocation = Path.of(this.fileStorageLocation + File.separator + dirName);
        
        Path filePath = newFileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        
        if(resource.exists()) {
            return resource;
        } else {
            return null;
        }
    }
    
    public boolean deleteFile(String filePathStr) throws IOException {
        if (filePathStr == null || filePathStr.isBlank() || !filePathStr.contains("/")) {
            return false;
        }
        
        String dirName = filePathStr.split("/")[0];
        String fileName = filePathStr.split("/")[1];
        
        Path newFileStorageLocation = Path.of(this.fileStorageLocation + File.separator + dirName);
        
        Files.deleteIfExists(Path.of(newFileStorageLocation + File.separator + fileName));
        
        return Files.exists(Path.of(newFileStorageLocation + File.separator + fileName));
    }
}
