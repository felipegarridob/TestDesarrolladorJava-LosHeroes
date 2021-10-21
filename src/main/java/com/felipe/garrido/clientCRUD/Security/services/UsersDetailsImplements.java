package com.felipe.garrido.clientCRUD.Security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.felipe.garrido.clientCRUD.models.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UsersDetailsImplements implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String _id;
    private String rut;
    private String firstName;
    private String lastName;
    private String username;
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UsersDetailsImplements(String _id, String rut,String firstName, String lastName, String username, String email, String password,
                                  Collection<? extends GrantedAuthority> authorities) {
        this._id = _id;
        this.rut = rut;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UsersDetailsImplements build(Users users) {
        List<GrantedAuthority> authorities = users.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UsersDetailsImplements(
                users.get_id(),
                users.getRut(),
                users.getFirstName(),
                users.getLastName(),
                users.getUsername(),
                users.getEmail(),
                users.getPassword(),
                 authorities);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    public String get_id(){return _id;}

    public String getRut() {
        return rut;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UsersDetailsImplements user = (UsersDetailsImplements) o;
        return Objects.equals(rut, user.rut);
    }



}
