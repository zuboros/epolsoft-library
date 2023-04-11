package com.example.epolsoftbackend.user.DTO;

import lombok.*;

@Builder
@Data
public class UserResponseDTO {

    private long id;

    private String name;

    private String mail;

    private boolean isBlocked;

}
