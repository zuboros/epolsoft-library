package com.example.epolsoftbackend.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService {

    UserDetails loadUserByUsername(String userMail) throws UsernameNotFoundException;

    UserDetails loadUserByMail(String mail) throws UsernameNotFoundException;

}
