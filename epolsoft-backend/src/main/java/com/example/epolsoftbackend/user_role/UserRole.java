package com.example.epolsoftbackend.user_role;

import com.example.epolsoftbackend.role.Role;
import com.example.epolsoftbackend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "user_role")
public class UserRole implements Serializable {

    @JsonIgnore
    @Id
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(
            name = "role_id",
            referencedColumnName = "id"
    )
    private Role role;
}
