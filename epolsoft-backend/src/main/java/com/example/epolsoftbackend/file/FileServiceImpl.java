package com.example.epolsoftbackend.file;

import java.io.File;

import com.example.epolsoftbackend.book.Book;
import com.example.epolsoftbackend.book.BookRepository;
import com.example.epolsoftbackend.exception.ForbiddenException;
import com.example.epolsoftbackend.exception.InternalServerErrorException;
import com.example.epolsoftbackend.exception.ResourceNotFoundException;
import com.example.epolsoftbackend.user.User;
import com.example.epolsoftbackend.user.UserDetailsImpl;
import com.example.epolsoftbackend.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
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
            throw new InternalServerErrorException("Failed to create new directories");
        }

        return dirTodayPathStr;
    }

    private void replaceOrCreateFile(MultipartFile file, Path dirPath, String fileUUIDName) {
        try {
            Files.copy(file.getInputStream(),
                    Path.of(dirPath + File.separator + fileUUIDName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new InternalServerErrorException("Failed to create a new file");
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

    public void deleteAvatarFile(long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));

        if (user.getAvatarPath() != null) {
            deleteFileByPath(convertToOsDependentFullFilePath(user.getAvatarPath()));
        }
    }
    
    public String storeFile(MultipartFile file, String type) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty or null");
        }

        if (type.equals("book")) {
            return createFile(file);
        } else if (type.equals("avatar")) {
            UserDetailsImpl userDetails = (UserDetailsImpl)
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String path = createFile(file);

            User user = userRepository.findById(userDetails.getId()).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", userDetails.getId()));

            if (user.getAvatarPath() != null) {
                deleteFileByPath(convertToOsDependentFullFilePath(user.getAvatarPath()));
            }

            user.setAvatarPath(path);
            userRepository.save(user);

            return path;
        } else {
            throw new RuntimeException("Could not determine file type. Pls enter smth from the list: [avatar, book]");
        }
    }
    
    public Resource loadFileAsResource(long id, String type) {
        if (type.equals("book")) {
            Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

            UserDetailsImpl userDetails = (UserDetailsImpl)
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!Objects.equals(book.getUserId().getId(), userDetails.getId())) {
                throw new ForbiddenException("User cannot download not his own books");
            }
        }

        String filePathStr;

        if (type.equals("book")) {
            filePathStr = bookRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Book", "id", id)).getFilePath();
        } else if (type.equals("avatar")) {
            filePathStr = userRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Book", "id", id)).getAvatarPath();
        } else {
            throw new RuntimeException("Could not determine file type. Pls enter smth from the list: [avatar, book]");
        }

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
            throw new RuntimeException("Malformed file URL");
        }

        if (resource.exists()) {
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
            throw new RuntimeException("Could not determine file type. Pls enter smth from the list: [avatar, book]");
        }
    }
    
    public void deleteBookFile(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(book.getUserId().getId(), userDetails.getId())) {
            throw new ForbiddenException("User cannot delete not his own book file");
        }
        deleteFileByPath(convertToOsDependentFullFilePath(book.getFilePath()));
    }
}
