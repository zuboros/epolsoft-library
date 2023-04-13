package com.example.epolsoftbackend.user.DTO;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    private long id;

    private String name;

    private String mail;

    private String password;

    private String avatarPath;
}
