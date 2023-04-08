package com.example.epolsoftbackend.user.DTO;

import com.example.epolsoftbackend.user_role.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Builder
@ToString
@Getter
@Setter
@Data
public class UserBookResponseDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("isBlocked")
    private boolean isBlocked;

    @JsonProperty("roles")
    private Set<UserRole> roles;

}
