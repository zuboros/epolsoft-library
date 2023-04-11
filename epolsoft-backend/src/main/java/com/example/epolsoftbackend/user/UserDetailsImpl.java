package com.example.epolsoftbackend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String name;
    private String mail;
    @JsonIgnore
    private String password;
    private boolean isBlocked;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String name, String mail, String password, boolean isBlocked, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.isBlocked = isBlocked;
        if (authorities == null) {
            this.authorities = null;
        } else {
            this.authorities = new ArrayList<>(authorities);
        }    }

    public static UserDetailsImpl create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getName())).collect(Collectors.toList());

        return new UserDetailsImpl(user.getId(), user.getName(),
                user.getMail(), user.getPasswordHash(), user.isBlocked(), authorities);
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities == null ? null : new ArrayList<>(authorities);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        UserDetailsImpl that = (UserDetailsImpl) object;
        return Objects.equals(id, that.id);
    }

}
