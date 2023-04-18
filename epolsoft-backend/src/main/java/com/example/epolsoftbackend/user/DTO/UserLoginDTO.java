package com.example.epolsoftbackend.user.DTO;

import lombok.*;

import javax.validation.constraints.Email;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @Email
    private String mail;

    private String password;

}
