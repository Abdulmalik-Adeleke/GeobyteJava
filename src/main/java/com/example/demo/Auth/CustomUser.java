package com.example.demo.Auth;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import com.example.demo.Auth.AuthModel.ApplicationUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUser implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> role;

    public CustomUser(Optional<ApplicationUser> user) {
        user.ifPresent(u ->{
            this.username = u.getEmail();
            this.password = u.getPassword();
            this.role = Collections.singletonList(new SimpleGrantedAuthority(u.getRole().getName()));
        });  
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
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

    
}