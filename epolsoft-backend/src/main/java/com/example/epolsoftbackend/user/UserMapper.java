package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.user.DTO.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //UserRequest
    public UserResponseDTO userToUserResponseDTO(User user);
    public User userResponseDTOToUser(UserResponseDTO userResponseDTO);
    public List<UserResponseDTO> listUserToListUserResponseDTO(List<User> userList);

    //UserRequest
    @Mapping(source = "user.roles", target = "roles")
    public UserBookResponseDTO userToUserBookResponseDTO(User user);
    @Mapping(source = "userBookResponseDTO.roles", target = "roles")
    public User userBookResponseDTOToUser(UserBookResponseDTO userBookResponseDTO);
    @Mapping(source = "user.roles", target = "roles")
    public List<UserBookResponseDTO> listUserToListUserBookResponseDTO(List<User> userList);

    //UserRequest
    @Mapping(source = "user.roles", target = "roles")
    public UserLoginResponseDTO userToUserLoginResponseDTO(User user);
    @Mapping(source = "userLoginResponseDTO.roles", target = "roles")
    public User userLoginResponseDTOToUser(UserLoginResponseDTO userLoginResponseDTO);
    @Mapping(source = "user.roles", target = "roles")
    public List<UserLoginResponseDTO> listUserToListUserLoginResponseDTO(List<User> userList);


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
