package com.example.epolsoftbackend.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String storeFile(MultipartFile file, String type, long userId);
    Resource loadFileAsResource(long id, String type, long userWhoDownloadId);
    void deleteBookFile(long id, long userWhoDeleteId);
    String receiveFileName(long id, String type);

}
