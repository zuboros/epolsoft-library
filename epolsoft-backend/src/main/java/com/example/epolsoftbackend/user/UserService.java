package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.user.DTO.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    ResponseEntity<List<UserResponseDTO>> getAllUsers();
    Optional<User> findById(long id);
    ResponseEntity<HttpStatus> deleteById(long id);

}
