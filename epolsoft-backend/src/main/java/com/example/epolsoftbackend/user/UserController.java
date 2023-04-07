package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.another.JsonWebToken;
import com.example.epolsoftbackend.user.DTO.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/authors")
public class UserController {

    final UserService userService;
    final UserMapper userMapper;
    final JsonWebToken jsonWebToken;

    public UserController(UserService userService, UserMapper userMapper, JsonWebToken jsonWebToken) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jsonWebToken = jsonWebToken;
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserBookResponseDTO>> getAllAuthors() {
        return userService.getAllUsers();
    }


    @PostMapping("/signup")
    public ResponseEntity<UserBookResponseDTO> signUp(
            @RequestBody UserRegistrationDTO userRegistrationDTO) {

        Optional<User> optUser = userService.findByMail(userRegistrationDTO.getMail());

        if (!optUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return userService.createNewUser(userRegistrationDTO);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserLoginResponseDTO> signIn(@RequestBody UserLoginDTO userLoginDTO) throws NoSuchAlgorithmException {
        Optional<User> optUser = userService.findByMail(userLoginDTO.getMail());

        if (optUser.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        User user = optUser.get();

        String hashedPass = userService.hashPassword(userLoginDTO.getPassword());

        if (userLoginDTO.getMail().equals(user.getMail())
                && hashedPass.equals(user.getPasswordHash()) && !user.isBlocked()) {
            UserLoginResponseDTO resultUser= userMapper.userToUserLoginResponseDTO(user);
            resultUser.setToken(jsonWebToken.generateToken(user));
            return new ResponseEntity<>(resultUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<UserResponseDTO> blockUser(@PathVariable("id") long id) {
        return userService.blockUser(id);
    }

    @PutMapping("/unblock/{id}")
    public ResponseEntity<UserResponseDTO> unblockUser(@PathVariable("id") long id) {
        return userService.unblockUser(id);
    }

}
