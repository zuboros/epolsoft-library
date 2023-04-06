package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.user.DTO.UserLoginDTO;
import com.example.epolsoftbackend.user.DTO.UserRegistrationDTO;
import com.example.epolsoftbackend.user.DTO.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(
            @RequestBody UserRegistrationDTO userRegistrationDTO) throws NoSuchAlgorithmException {

        Optional<User> optUser = userService.findByMail(userRegistrationDTO.getMail());

        if (!optUser.isEmpty()) {
            return null;
        }

        userService.createNewUser(userRegistrationDTO);

        return null; //<--- return token
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody UserLoginDTO userLoginDTO) throws NoSuchAlgorithmException {
        Optional<User> optUser = userService.findByMail(userLoginDTO.getMail());

        if (optUser.isEmpty()) {
            return null;
        }

        User user = optUser.get();

        String hashedPass = userService.hashPassword(userLoginDTO.getPassword());

        if (userLoginDTO.getMail().equals(user.getMail()) && hashedPass.equals(user.getPasswordHash())) {
            //return token
        }

        return null;
    }

}
