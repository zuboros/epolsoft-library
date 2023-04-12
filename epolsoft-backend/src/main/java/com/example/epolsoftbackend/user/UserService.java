package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.user.DTO.*;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserBookResponseDTO> getAllUsers();
    Optional<User> findById(long id);
    Optional<User> findByMail(String mail);
    boolean deleteById(long id);
    UserBookResponseDTO createNewUser(UserRegistrationDTO userRegistrationDTO);
    UserLoginResponseDTO login(UserLoginDTO userLoginDTO);
    UserResponseDTO blockUser(long id);
    UserResponseDTO unblockUser(long id);

}
