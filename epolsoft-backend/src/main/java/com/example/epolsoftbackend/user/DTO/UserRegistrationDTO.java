package com.example.epolsoftbackend.user.DTO;

import lombok.*;

@Builder
@Data
public class UserRegistrationDTO {

    private String name;

    private String mail;

    private String password;

}
