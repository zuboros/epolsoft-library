package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.user.DTO.*;
import com.example.epolsoftbackend.user_role.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //UserRequest
    public UserResponseDTO userToUserResponseDTO(User user);
    public User userResponseDTOToUser(UserResponseDTO userResponseDTO);

    //UserRequest
    @Mapping(source = "user.roles", target = "roles")
    public default UserBookResponseDTO userToUserBookResponseDTO(User user){
            if ( user == null ) {
                return null;
            }

            UserBookResponseDTO.UserBookResponseDTOBuilder userBookResponseDTO = UserBookResponseDTO.builder();

            Set<String> roles = new HashSet<>();
            for (UserRole userRole: user.getRoles()) {
                roles.add(userRole.getRole().getName());
            }
            userBookResponseDTO.roles(roles);

            if ( user.getId() != null ) {
                userBookResponseDTO.id( user.getId() );
            }
            userBookResponseDTO.name( user.getName() );
            userBookResponseDTO.mail( user.getMail() );

            return userBookResponseDTO.build();
    }

    @Mapping(source = "user.roles", target = "roles")
    public default List<UserBookResponseDTO> listUserToListUserBookResponseDTO(List<User> userList){
        if ( userList == null ) {
            return null;
        }

        List<UserBookResponseDTO> list = new ArrayList<UserBookResponseDTO>( userList.size() );
        for ( User user : userList ) {
            list.add( userToUserBookResponseDTO( user ) );
        }

        return list;
    }

    //UserRequest
    @Mapping(source = "user.roles", target = "roles")
    public default UserLoginResponseDTO userToUserLoginResponseDTO(User user) {
        if ( user == null ) {
            return null;
        }
        UserLoginResponseDTO.UserLoginResponseDTOBuilder userLoginResponseDTO = UserLoginResponseDTO.builder();


        Set<String> roles = new HashSet<>();
        for (UserRole userRole: user.getRoles()) {
            roles.add(userRole.getRole().getName());
        }
        userLoginResponseDTO.roles(roles);

        if ( user.getId() != null ) {
            userLoginResponseDTO.id( user.getId() );
        }
        userLoginResponseDTO.name( user.getName() );
        userLoginResponseDTO.mail( user.getMail() );

        return userLoginResponseDTO.build();
    }

    @Mapping(source = "user.roles", target = "roles")
    public List<UserLoginResponseDTO> listUserToListUserLoginResponseDTO(List<User> userList);

}
