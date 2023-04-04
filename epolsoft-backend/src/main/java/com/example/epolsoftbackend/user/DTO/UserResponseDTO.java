package com.example.epolsoftbackend.user.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@ToString
@Getter
@Setter
@Data
public class UserResponseDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("isBlocked")
    private boolean isBlocked;

    //maybe time in response
}
