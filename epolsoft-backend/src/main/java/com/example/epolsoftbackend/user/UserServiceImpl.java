package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.role.Role;
import com.example.epolsoftbackend.role.RoleRepository;
import com.example.epolsoftbackend.user.DTO.UserLoginDTO;
import com.example.epolsoftbackend.user.DTO.UserRegistrationDTO;
import com.example.epolsoftbackend.user.DTO.UserResponseDTO;
import com.example.epolsoftbackend.user_role.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return new ResponseEntity<>(userMapper.listUserToListUserResponseDTO(userRepository.findAll()), HttpStatus.OK);
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    public ResponseEntity<HttpStatus> deleteById(long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<UserResponseDTO> createNewUser(UserRegistrationDTO userRegistrationDTO) {
        User newUser = new User();
        Role role = roleRepository.findByName("USER").get();

        newUser.setMail(userRegistrationDTO.getMail());
        newUser.setName(userRegistrationDTO.getName());
        try {
            newUser.setPasswordHash(hashPassword(userRegistrationDTO.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        newUser.setBlocked(false);
        newUser.getRoles().add(new UserRole(newUser, role));

        return new ResponseEntity<>(userMapper.userToUserResponseDTO(userRepository.save(newUser)), HttpStatus.CREATED);
    }

    public String hashPassword(String pass) throws NoSuchAlgorithmException {
        MessageDigest crypt = MessageDigest.getInstance("SHA-512");
        crypt.update(pass.getBytes(StandardCharsets.UTF_8));

        byte[] bytes = crypt.digest();
        BigInteger bi = new BigInteger(1, bytes);

        return String.format("%0" + (bytes.length << 1) + "x", bi);
    }

}
