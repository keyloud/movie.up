package com.kosavpa.first.boot.example.dao.entity.users;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Collection;


@Data
@Entity
@NoArgsConstructor
@Table(name = "user_entity")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    @NotNull(message = "Имя пользователя не может быть пустым!")
    @Size(min = 3, max = 10, message = "Имя пользователя должно содержать от 3 до 10 знаков включительно!")
    private String username;
    @Column(name = "password")
    @NotNull(message = "Пароль не должен быть пустым!")
    @Size(min = 3, max = 24, message = "Пароль должен содержать от 3 до 24 знаков включительно!")
    private String password;
    @Column(name = "role")
    private String role;

    public UserEntity(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }
}