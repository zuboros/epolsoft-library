package com.example.epolsoftbackend.user.DTO;

import lombok.*;

@Builder
@Data
public class UserLoginDTO {

    private String mail;

    private String password;

}
