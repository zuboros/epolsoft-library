package com.example.epolsoftbackend.user.DTO;

import lombok.*;

@Builder
@Data
public class UserUpdateDTO {

    private long id;

    private String name;

    private String mail;

    private String password;

    private String avatarName;

    private String avatarPath;
}
