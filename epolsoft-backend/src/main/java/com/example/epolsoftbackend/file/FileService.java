package com.example.epolsoftbackend.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String storeFile(MultipartFile file, String type);
    Resource loadFileAsResource(long id, String type);
    void deleteBookFile(long id);
    String receiveFileName(long id, String type);

}
