package com.example.epolsoftbackend.user.DTO;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    private String name;

    private String mail;

    private String password;

}
