package com.example.epolsoftbackend.services;

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
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@Service
public class FileService {
    private final Path fileStorageLocation = Path.of(
            System.getProperty("user.dir") + File.separator + "bookCollection");
    
    public String storeFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
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
        
        Files.createDirectories(dirTodayPath);
        
        String fileUUIDName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
        System.out.println(fileUUIDName);

        Files.copy(file.getInputStream(),
                Path.of(dirTodayPath + File.separator + fileUUIDName),
                StandardCopyOption.REPLACE_EXISTING);
        
        return dirTodayPathStr + "/" + fileUUIDName;
    }
    
    public Resource loadFileAsResource(String filePathStr) throws MalformedURLException {
        if (filePathStr == null
                || filePathStr.isBlank()
                || !filePathStr.contains("/")) {
            return null;
        }
        
        String[] filePathStrArray = filePathStr.split("/");
        
        String dirPathStr = filePathStrArray[0] + File.separator
                + filePathStrArray[1] + File.separator
                + filePathStrArray[2];
        
        String fileName = filePathStrArray[filePathStrArray.length - 1];
        
        Path newFileStorageLocation = Path.of(this.fileStorageLocation
                + File.separator + dirPathStr);
        
        Path filePath = newFileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        
        if(resource.exists()) {
            return resource;
        } else {
            return null;
        }
    }
    
    public boolean deleteFile(String filePathStr) throws IOException {
        if (filePathStr == null
                || filePathStr.isBlank()
                || !filePathStr.contains("/")) {
            return false;
        }
        
        String[] filePathStrArray = filePathStr.split("/");
        
        String dirPathStr = filePathStrArray[0] + File.separator
                + filePathStrArray[1] + File.separator
                + filePathStrArray[2];
        
        String fileName = filePathStrArray[filePathStrArray.length - 1];
        
        Path newFileStorageLocation = Path.of(this.fileStorageLocation 
                + File.separator + dirPathStr);
        
        Files.deleteIfExists(Path.of(newFileStorageLocation
                + File.separator + fileName));
        
        return Files.exists(Path.of(newFileStorageLocation
                + File.separator + fileName));
    }
}
