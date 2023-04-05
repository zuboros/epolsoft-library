package com.example.epolsoftbackend.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface FileService {

    String storeFile(MultipartFile file) throws IOException;
    Resource loadFileAsResource(String filePathStr) throws MalformedURLException;
    boolean deleteFile(String filePathStr) throws IOException;

}
