package com.example.epolsoftbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JsonWebTokenProvider jsonWebTokenProvider;

    public SecurityConfig(JsonWebTokenProvider jsonWebTokenProvider) {
        this.jsonWebTokenProvider = jsonWebTokenProvider;
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .cors().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/library/**", "/api/authors/signup", "/api/authors/signin", "/swagger-ui.html").permitAll()
                .antMatchers("/api/file/download/",
                        "/api/topic/get/available",
                        "/api/book",
                        "/api/file/upload",
                        "/api/file/delete",
                        "/api/book/update",
                        "/api/book/delete").hasRole("USER")
                .antMatchers("/api/authors/get",
                        "/api/topic/get/all",
                        "/api/topic/create",
                        "/api/topic/delete").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JsonWebTokenConfigurer(jsonWebTokenProvider));

        return http.build();
    }
}