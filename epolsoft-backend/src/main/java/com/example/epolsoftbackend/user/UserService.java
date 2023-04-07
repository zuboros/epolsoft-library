package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.user.DTO.UserBookResponseDTO;
import com.example.epolsoftbackend.user.DTO.UserRegistrationDTO;
import com.example.epolsoftbackend.user.DTO.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    ResponseEntity<List<UserBookResponseDTO>> getAllUsers();
    Optional<User> findById(long id);
    Optional<User> findByMail(String mail);
    ResponseEntity<HttpStatus> deleteById(long id);
    ResponseEntity<UserBookResponseDTO> createNewUser(UserRegistrationDTO userRegistrationDTO);
    String hashPassword(String pass) throws NoSuchAlgorithmException;

}
