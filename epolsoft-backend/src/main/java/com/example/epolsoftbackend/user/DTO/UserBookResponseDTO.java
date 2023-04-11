package com.example.epolsoftbackend.user.DTO;

import com.example.epolsoftbackend.role.Role;
import lombok.*;

import java.util.Set;

@Builder
@Data
public class UserBookResponseDTO {

    private long id;

    private String name;

    private String mail;

    private boolean isBlocked;

    private Set<String> roles;

}
