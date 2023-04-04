package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.user.DTO.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserResponseDTO>> getAllAuthors() {
        return userService.getAllUsers();
    }

    //@Post
}
