package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.exception.*;
import com.example.epolsoftbackend.file.FileService;
import com.example.epolsoftbackend.role.Role;
import com.example.epolsoftbackend.role.RoleRepository;
import com.example.epolsoftbackend.security.JsonWebTokenProvider;
import com.example.epolsoftbackend.user.DTO.*;
import com.example.epolsoftbackend.user_role.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final FileService fileService;

    private boolean containsRole(final List<UserRole> list, final String roleName) {
        return list.stream().anyMatch(o -> o.getRole().getName().equals(roleName));
    }

    public List getAllUsers(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        List<User> users = page.getContent();
        return List.of(userMapper.listUserToListUserBookResponseDTO(users), page.getTotalElements());
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    public void deleteById(long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("User", "id", id));
            userRepository.deleteById(id);
            fileService.deleteAvatarFile(user.getId());
        } catch (Exception e) {
            throw new InternalServerErrorException("Exception occurred while user is deleting");
        }
    }

    public UserResponseDTO updateUser(UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(userUpdateDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userUpdateDTO.getId()));

        if (userUpdateDTO.getAvatarPath() != null && !user.getAvatarPath().equals(userUpdateDTO.getAvatarPath())) {
            user.setAvatarPath(userUpdateDTO.getAvatarPath());
            fileService.deleteAvatarFile(userUpdateDTO.getId());
        }

        if (userUpdateDTO.getName() != null) {
            user.setName(userUpdateDTO.getName());
        }

        return userMapper.userToUserResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO updateUserPassword(UserUpdatePasswordDTO userUpdatePasswordDTO) {
        User user = userRepository.findById(userUpdatePasswordDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userUpdatePasswordDTO.getId()));

        if (!bCryptPasswordEncoder.matches(userUpdatePasswordDTO.getOldPassword(), user.getPasswordHash())) {
            throw new ForbiddenException("Password mismatch");
        }

        user.setPasswordHash(bCryptPasswordEncoder.encode(userUpdatePasswordDTO.getNewPassword()));

        return userMapper.userToUserResponseDTO(userRepository.save(user));
    }

    public UserBookResponseDTO createNewUser(UserRegistrationDTO userRegistrationDTO) {
        Role role = roleRepository.findByName("USER").orElseThrow(
                () -> new ResourceNotFoundException("Role", "name", "USER"));

        User newUser = new User();

        newUser.setMail(userRegistrationDTO.getMail());
        newUser.setName(userRegistrationDTO.getName());

        try {
            newUser.setPasswordHash(bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword()));
            System.out.println(newUser.getPasswordHash());
        } catch (Exception e) {
            throw new InternalServerErrorException("Error occurred while password is encoding");
        }
        newUser.setPasswordUpdatedAt(LocalDateTime.now());

        newUser.setBlocked(false);
        newUser.getRoles().add(new UserRole(newUser, role));
        newUser.setPasswordUpdatedAt(LocalDateTime.now());

        return userMapper.userToUserBookResponseDTO(userRepository.save(newUser));
    }

    //public boolean

    public UserLoginResponseDTO login(UserLoginDTO userLoginDTO) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getMail(),
                            userLoginDTO.getPassword()));
            User user = userRepository.findByMail(userLoginDTO.getMail()).orElseThrow(
                    () -> new ResourceNotFoundException("User", "mail", userLoginDTO.getMail()));

            //SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //UserLoginResponseDTO resultUser = userMapper.userToUserLoginResponseDTO(au);

            UserLoginResponseDTO resultUser = userMapper.userToUserLoginResponseDTO(user);
            resultUser.setToken(jsonWebTokenProvider.generateToken(user));

            return resultUser;
        } catch (Exception e) {
            throw new UnauthorizedException("User does not exist or password is incorrect");
        }
    }

    public UserResponseDTO blockUser(long id) {
        User userNeedToBlock = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id));

        if (containsRole(userNeedToBlock.getRoles(), "ADMIN")) {
            throw new ForbiddenException("Can't block administrator");
        }

        userNeedToBlock.setBlocked(true);

        return userMapper.userToUserResponseDTO(userRepository.save(userNeedToBlock));
    }

    public UserResponseDTO unblockUser(long id) {
        User userNeedToUnblock = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id));

        if (containsRole(userNeedToUnblock.getRoles(), "ADMIN")) {
            throw new ForbiddenException("Can't unblock administrator");
        }

        userNeedToUnblock.setBlocked(false);

        return userMapper.userToUserResponseDTO(userRepository.save(userNeedToUnblock));
    }

    public boolean isExpired(LocalDateTime userPasswordUpdatedAt) {
        UserDetailsImpl userDetails = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userDetails.getId()));
        return userRepository.isPasswordExpired(user.getPasswordUpdatedAt());
    }

    public int howManyDaysNotification() {
        UserDetailsImpl userDetails = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userDetails.getId()));
        return userRepository.howManyDaysNotification(user.getPasswordUpdatedAt());
    }

}
