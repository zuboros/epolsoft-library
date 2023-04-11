package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.exception.BadRequestException;
import com.example.epolsoftbackend.exception.ForbiddenException;
import com.example.epolsoftbackend.exception.ResourceNotFoundException;
import com.example.epolsoftbackend.role.Role;
import com.example.epolsoftbackend.role.RoleRepository;
import com.example.epolsoftbackend.security.JsonWebTokenProvider;
import com.example.epolsoftbackend.user.DTO.*;
import com.example.epolsoftbackend.user_role.UserRole;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JsonWebTokenProvider jsonWebTokenProvider;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, PasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JsonWebTokenProvider jsonWebTokenProvider) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jsonWebTokenProvider = jsonWebTokenProvider;
    }

    public List<UserBookResponseDTO> getAllUsers() {
        return userMapper.listUserToListUserBookResponseDTO(userRepository.findAll());
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    public boolean deleteById(long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new BadRequestException("Internal server error");
        }
    }

    public UserBookResponseDTO createNewUser(UserRegistrationDTO userRegistrationDTO) {
        Role role = roleRepository.findByName("USER").get();

        User newUser = new User();

        newUser.setMail(userRegistrationDTO.getMail());
        newUser.setName(userRegistrationDTO.getName());
        try {
            newUser.setPasswordHash(bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword()));
            System.out.println(newUser.getPasswordHash());
        } catch (Exception e) {
            throw new InternalError("Error encoding password");
        }

        newUser.setBlocked(false);
        newUser.getRoles().add(new UserRole(newUser, role));

        return userMapper.userToUserBookResponseDTO(userRepository.save(newUser));
    }

    public UserLoginResponseDTO login(UserLoginDTO userLoginDTO) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getMail(), userLoginDTO.getPassword()));
            Optional<User> optUser = userRepository.findByMail(userLoginDTO.getMail());
            if (optUser.isEmpty()) throw new BadRequestException("not exist");
            //SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //UserLoginResponseDTO resultUser = userMapper.userToUserLoginResponseDTO(au);
            UserLoginResponseDTO resultUser = userMapper.userToUserLoginResponseDTO(optUser.get());
            resultUser.setToken(jsonWebTokenProvider.generateToken(optUser.get()));

            return resultUser;
        } catch (Exception e) {
            throw new ForbiddenException("UNAUTHORIZED");
        }
    }


    public UserResponseDTO blockUser(long id) {
        User userNeedToBlock = userRepository.findById(id).get();

        if (containsRole(userNeedToBlock.getRoles(), "ADMIN")) {
            throw new ForbiddenException("Can't block administrator");
        }

        userNeedToBlock.setBlocked(true);

        return userMapper.userToUserResponseDTO(userRepository.save(userNeedToBlock));
    }

    public UserResponseDTO unblockUser(long id) {
        User userNeedToUnblock = userRepository.findById(id).get();

        if (containsRole(userNeedToUnblock.getRoles(), "ADMIN")) {
            throw new ForbiddenException("Can't unblock administrator");
        }

        userNeedToUnblock.setBlocked(false);

        return userMapper.userToUserResponseDTO(userRepository.save(userNeedToUnblock));
    }

    private boolean containsRole(final List<UserRole> list, final String roleName){
        return list.stream().filter(o -> o.getRole().getName().equals(roleName)).findFirst().isPresent();
    }

}
