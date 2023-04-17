package com.example.epolsoftbackend.user.DTO;

import com.example.epolsoftbackend.validation.PasswordConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePasswordDTO {

    private long id;

    @PasswordConstraint
    private String oldPassword;

    @PasswordConstraint
    private String newPassword;

}
