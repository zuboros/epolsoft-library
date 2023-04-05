package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.user.DTO.UserLoginDTO;
import com.example.epolsoftbackend.user.DTO.UserRegistrationDTO;
import com.example.epolsoftbackend.user.DTO.UserResponseDTO;
import com.example.epolsoftbackend.user.DTO.UserUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //UserRequest
    public UserResponseDTO userToUserResponseDTO(User user);
    public User userResponseDTOToUser(UserResponseDTO userResponseDTO);
    public List<UserResponseDTO> listUserToListUserResponseDTO(List<User> userList);

    //UserRegistration
    public UserRegistrationDTO userToUserRegistrationDTO(User user);
    public User userRegistrationDTOToUser(UserRegistrationDTO userRegistrationDTO);
    public List<UserRegistrationDTO> listUserToListUserRegistrationDTO(List<User> userList);

    //UserLogin
    public UserLoginDTO userToUserLoginDTO(User user);
    public User userLoginDTOToUser(UserLoginDTO userLoginDTO);
    public List<UserLoginDTO> listUserToListUserLoginDTO(List<User> userList);

    //UserUpdate
    public UserUpdateDTO userToUserUpdateDTO(User user);
    public User userUpdateDTOToUser(UserUpdateDTO userUpdateDTO);
    public List<UserUpdateDTO> listUserToListUserUpdateDTO(List<User> userList);
}
