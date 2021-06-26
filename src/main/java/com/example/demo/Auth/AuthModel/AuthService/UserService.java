package com.example.demo.Auth.AuthModel.AuthService;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;

import com.example.demo.Auth.AuthModel.ApplicationUser;
import com.example.demo.Auth.AuthModel.Role;
import com.example.demo.Auth.AuthModel.UserDto;
import com.example.demo.Auth.AuthModel.AuthRepo.RoleRepository;
import com.example.demo.Auth.AuthModel.AuthRepo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ApplicationUser saveUser(UserDto userdto) {

        // random password string generator
        byte[] array = new byte[7];  // length is bounded by 7
        new Random().nextBytes(array);
        String generatedPasswordPlaceholder = new String(array, Charset.forName("UTF-8"));

        ApplicationUser user = new ApplicationUser();
        Role role = roleRepo.findByName(userdto.getRole());
        user.setName(userdto.getName());
        user.setEmail(userdto.getEmail());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(generatedPasswordPlaceholder));
        return userRepo.save(user);
    }

    public ApplicationUser login(String username, String password) {
        Optional<ApplicationUser> user = userRepo.findById(username);

        if (user.isPresent()) {
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                return user.get();
            }
        }
        return null;
    }

    public Boolean updatePassword(String username, String password) {

        Optional<ApplicationUser> user = userRepo.findById(username);
        if (user.isPresent()) {
            String pass = passwordEncoder.encode(password);
            try {
                userRepo.updatePassword(username, pass);
            } catch (Exception e) {
                 // more specific error message pls
                return false;
            }
            return true;
        }
        // more specific error message pls
        // only if white house is down
        return false;
    }

}
