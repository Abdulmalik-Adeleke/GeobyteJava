package com.example.demo.Auth;

import java.util.Optional;

import com.example.demo.Auth.AuthModel.ApplicationUser;
import com.example.demo.Auth.AuthModel.AuthRepo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepo;

    @Autowired
    public CustomUserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public CustomUser loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<ApplicationUser> user = userRepo.findById(email);
        CustomUser customuser = new CustomUser(user);
        return customuser;
    }
    
}
