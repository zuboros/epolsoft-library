package com.example.epolsoftbackend.user.DTO;

import com.example.epolsoftbackend.validation.PasswordConstraint;
import lombok.*;

import javax.validation.constraints.Email;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    private String name;

    @Email
    private String mail;

    @PasswordConstraint
    private String password;

}
