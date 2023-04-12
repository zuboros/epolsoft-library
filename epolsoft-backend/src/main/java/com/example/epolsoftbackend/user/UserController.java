package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.security.JsonWebTokenProvider;
import com.example.epolsoftbackend.user.DTO.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/authors")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserBookResponseDTO>> getAllAuthors() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity<UserBookResponseDTO> signUp(@RequestBody UserRegistrationDTO userRegistrationDTO) {

        Optional<User> optUser = userService.findByMail(userRegistrationDTO.getMail());
        if (optUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(userService.createNewUser(userRegistrationDTO),HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserLoginResponseDTO> signIn(@RequestBody UserLoginDTO userLoginDTO) throws NoSuchAlgorithmException {
        return new ResponseEntity<>(userService.login(userLoginDTO), HttpStatus.OK);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<UserResponseDTO> blockUser(@PathVariable("id") long id) {
        return new ResponseEntity<>(userService.blockUser(id), HttpStatus.OK);
    }

    @PutMapping("/unblock/{id}")
    public ResponseEntity<UserResponseDTO> unblockUser(@PathVariable("id") long id) {
        return new ResponseEntity<>(userService.unblockUser(id), HttpStatus.OK);
    }

}
