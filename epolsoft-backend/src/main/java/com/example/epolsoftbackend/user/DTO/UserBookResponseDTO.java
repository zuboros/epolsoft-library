package com.example.epolsoftbackend.user.DTO;

import lombok.*;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookResponseDTO {

    private long id;

    private String name;

    private String mail;

    private boolean isBlocked;

    private Set<String> roles;

}
