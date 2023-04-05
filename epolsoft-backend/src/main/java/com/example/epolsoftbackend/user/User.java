package com.example.epolsoftbackend.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import com.example.epolsoftbackend.book.Book;
import com.example.epolsoftbackend.user_role.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Email
    @Length(min = 0, max = 255)
    @Column(name = "mail")
    private String mail;

    @Column(name = "password_hash")
    @Length(min = 1, max = 255)
    private String passwordHash;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "avatar")
    @Length(min = 0, max = 255)
    private String avatar;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<UserRole> roles;

    @JsonIgnore
    @OneToMany(
            mappedBy = "userId",
            cascade = CascadeType.ALL
    )
    private Set<Book> books;
}
