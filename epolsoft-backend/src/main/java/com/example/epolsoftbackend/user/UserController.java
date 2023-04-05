package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.user.DTO.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class UserController {

    final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserResponseDTO>> getAllAuthors() {
        return userService.getAllUsers();
    }

//
//    @PostMapping("/signup")
//    public ResponseEntity<> signUp(@RequestBody UserRegistrationDTO userRegistrationDTO) {
//        userService.
//        //return token if goot and error if created or more
//        //+ hash password
//        return null;
//    }
//    public ResponseEntity<> signIn(@RequestBody UserLoginDTO userLoginDTO) {
//        return null;
//    }
}
