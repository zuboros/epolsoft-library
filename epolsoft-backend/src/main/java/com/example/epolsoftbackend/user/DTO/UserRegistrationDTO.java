package com.example.epolsoftbackend.user.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@ToString
@Getter
@Setter
@Data
public class UserRegistrationDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("password")
    private String password;

}
