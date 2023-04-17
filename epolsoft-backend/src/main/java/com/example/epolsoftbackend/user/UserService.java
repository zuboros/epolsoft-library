package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.user.DTO.*;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List getAllUsers(Pageable pageable);
    Optional<User> findById(long id);
    Optional<User> findByMail(String mail);
    void deleteById(long id);
    UserBookResponseDTO createNewUser(UserRegistrationDTO userRegistrationDTO);
    UserLoginResponseDTO login(UserLoginDTO userLoginDTO);
    UserResponseDTO blockUser(long id);
    UserResponseDTO unblockUser(long id);
    boolean isExpired(LocalDateTime userPasswordUpdatedAt);
    int howManyDaysNotification();
    UserResponseDTO updateUser(UserUpdateDTO userUpdateDTO);
    UserResponseDTO updateUserPassword(UserUpdatePasswordDTO userUpdatePasswordDTO);

}
