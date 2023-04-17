package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.topic.TopicService;
import com.example.epolsoftbackend.user.DTO.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import java.security.NoSuchAlgorithmException;

@RestController
@CrossOrigin
@RequestMapping("/api/authors")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/howManyDaysNotification")
    public ResponseEntity<Integer> howManyDaysNotification() {
        return new ResponseEntity<>(userService.howManyDaysNotification(), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List> getAllAuthors(Pageable pageable) {
        return new ResponseEntity<>(userService.getAllUsers(pageable), HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity<UserBookResponseDTO> signUp(@RequestBody UserRegistrationDTO userRegistrationDTO) throws NoSuchFieldException {

        Optional<User> optUser = userService.findByMail(userRegistrationDTO.getMail());
        if (optUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(userService.createNewUser(userRegistrationDTO), HttpStatus.CREATED);
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

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        return new ResponseEntity<>(userService.updateUser(userUpdateDTO), HttpStatus.OK);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<UserResponseDTO> updateUserPassword(@RequestBody @Valid UserUpdatePasswordDTO userUpdatePasswordDTO) {
        return new ResponseEntity<>(userService.updateUserPassword(userUpdatePasswordDTO), HttpStatus.OK);
    }

}
