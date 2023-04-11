package com.example.epolsoftbackend.user;

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
        Optional<User> user = userService.findByMail(userMail);
        if (user.isEmpty()) throw new UsernameNotFoundException("user with name: " + userMail + " is not found");
        return UserDetailsImpl.create(user.get());
    }

    public UserDetails loadUserByMail(String mail) throws UsernameNotFoundException {
        Optional<User> user = userService.findByMail(mail);
        if (user.isEmpty()) throw new UsernameNotFoundException("user with mail: " + mail + " is not found");
        return UserDetailsImpl.create(user.get());
    }

}
