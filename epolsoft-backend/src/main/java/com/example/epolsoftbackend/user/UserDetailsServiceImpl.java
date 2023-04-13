package com.example.epolsoftbackend.user;

import com.example.epolsoftbackend.exception.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {

    private final UserRepository userService;

    public UserDetailsServiceImpl(UserRepository userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException {
        User user = userService.findByMail(userMail).orElseThrow(
                () -> new ResourceNotFoundException("User", "mail", userMail));
        return UserDetailsImpl.create(user);
    }

    public UserDetails loadUserByMail(String userMail) throws UsernameNotFoundException {
        User user = userService.findByMail(userMail).orElseThrow(
                () -> new ResourceNotFoundException("User", "mail", userMail));
        return UserDetailsImpl.create(user);
    }

}
