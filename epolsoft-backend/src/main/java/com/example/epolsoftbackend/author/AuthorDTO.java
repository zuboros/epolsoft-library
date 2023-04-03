package com.example.epolsoftbackend.author;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@ToString
@Getter
@Setter
@Data
public class AuthorDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;
}
