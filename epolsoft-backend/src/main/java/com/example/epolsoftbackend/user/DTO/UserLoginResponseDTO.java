package com.example.epolsoftbackend.user.DTO;

import com.example.epolsoftbackend.role.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Builder
@Data
public class UserLoginResponseDTO {

    private long id;

    private String name;

    private String mail;

    private boolean isBlocked;

    @JsonProperty("roles")
    private Set<String> roles;

    private String token;
}
