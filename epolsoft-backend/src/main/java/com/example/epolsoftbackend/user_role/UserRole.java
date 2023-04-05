package com.example.epolsoftbackend.user_role;

import com.example.epolsoftbackend.role.Role;
import com.example.epolsoftbackend.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {
    @JsonIgnore
    @Id
    @ManyToOne
    private User user;
    @Id
    @ManyToOne
    private Role role;
}
