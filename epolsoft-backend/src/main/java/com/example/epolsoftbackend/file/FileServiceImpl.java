package com.example.epolsoftbackend.file;

import java.io.File;

import com.example.epolsoftbackend.book.Book;
import com.example.epolsoftbackend.book.BookRepository;
import com.example.epolsoftbackend.exception.ForbiddenException;
import com.example.epolsoftbackend.exception.ResourceNotFoundException;
import com.example.epolsoftbackend.user.User;
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
            System.getProperty("user.dir") + File.separator + "fileStorage");
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public FileServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    private boolean isWindows(String os) {
        return (os.indexOf("win") >= 0);
    }

    private String convertToOsDependentFormatStr(String pathStr) {
        return pathStr.replaceAll("/",
                isWindows(System.getProperty("os.name").toLowerCase()) ? "\\\\" : File.separator);
    }

    private Path convertToFullFileStoragePath(String pathStr) {
        return Path.of(this.fileStorageLocation + File.separator + pathStr);
    }

    private Path convertToOsDependentFullFilePath(String filePathStr) {
        String OsDependentFilePath = convertToOsDependentFormatStr(filePathStr);

        return convertToFullFileStoragePath(OsDependentFilePath);
    }

    private void deleteFileByPath(Path location) {
        try {
            Files.deleteIfExists(location);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException occurred while file is deleting");
        }
    }

    private String createTodayDirectories() {
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

        return dirTodayPathStr;
    }

    private void replaceOrCreateFile(MultipartFile file, Path dirPath, String fileUUIDName) {
        try {
            Files.copy(file.getInputStream(),
                    Path.of(dirPath + File.separator + fileUUIDName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create a new file");
        }
    }

    private String createFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = fileName.substring(fileName.lastIndexOf("."));

        String dirPath = createTodayDirectories();

        String fileUUIDName = UUID.randomUUID() + ext;

        replaceOrCreateFile(file, convertToOsDependentFullFilePath(dirPath), fileUUIDName);

        return dirPath + "/" + fileUUIDName;
    }

    public void deleteAvatarFile(String filePath, long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));

        if (user.getAvatarPath() != null) {
            deleteFileByPath(convertToOsDependentFullFilePath(filePath));
        }
    }
    
    public String storeFile(MultipartFile file, String type, long userId) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty or null");
        }

        if (type.equals("book")) {
            return createFile(file);
        } else if (type.equals("avatar")) {
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", userId));
            String path = createFile(file);

            if (user.getAvatarPath() != null) {
                deleteFileByPath(convertToOsDependentFullFilePath(user.getAvatarPath()));
            }

            user.setAvatarPath(path);
            userRepository.save(user);

            return path;
        } else {
            throw new RuntimeException("Could not determine type. Please enter smth from the list: [avatar, book]");
        }
    }
    
    public Resource loadFileAsResource(long id, String type, long userWhoDownloadId) {
        if (type.equals("book")) {
            Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

            if (book.getUserId().getId() != userWhoDownloadId) {
                throw new ForbiddenException("User cannot download not his own books");
            }
        }

        String filePathStr = type.equals("book") ?
                bookRepository.findById(id).get().getFilePath() : userRepository.findById(id).get().getAvatarPath();

        if (filePathStr == null) {
            throw new ResourceNotFoundException("File", "filePathStr", null);
        }

        if (filePathStr.isBlank() || !filePathStr.contains("/")) {
            throw new RuntimeException("Incorrect file path stored in DB");
        }

        Path filePath = convertToOsDependentFullFilePath(filePathStr).normalize();

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
            throw new ResourceNotFoundException("Resource", "filePath", filePath);
        }
    }

    public String receiveFileName(long id, String type) {
        if (type.equals("book")) {
            return bookRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Book", "id", id)).getFileName();
        } else if (type.equals("avatar")) {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", id));
            return user.getAvatarPath().substring(user.getAvatarPath().lastIndexOf("/") + 1);
        } else {
            throw new RuntimeException("Could not determine type. Please enter smth from the list: [avatar, book]");
        }
    }
    
    public void deleteBookFile(long id, long userWhoDeleteId) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

        if (!Objects.equals(book.getUserId().getId(), userWhoDeleteId)) {
            throw new ForbiddenException("User cannot delete not his own books");
        }

        deleteFileByPath(convertToOsDependentFullFilePath(book.getFilePath()));
    }
}
