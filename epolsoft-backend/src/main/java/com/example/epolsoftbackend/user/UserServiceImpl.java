package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.role.Role;
import com.example.epolsoftbackend.user.DTO.UserLoginDTO;
import com.example.epolsoftbackend.user.DTO.UserRegistrationDTO;
import com.example.epolsoftbackend.user.DTO.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return new ResponseEntity<>(userMapper.listUserToListUserResponseDTO(userRepository.findAll()), HttpStatus.OK);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public ResponseEntity<HttpStatus> deleteById(long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public ResponseEntity<> createNewUser(UserRegistrationDTO userRegistrationDTO){
//            User newUser = new User();
//            newUser.setMail();
//            Role role = new Role();
//            newUser.getRoles().add();
//            newUser.setBlocked(false);
//    }
}
