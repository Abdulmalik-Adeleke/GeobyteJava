package com.example.demo.Controller;

import java.util.UUID;

import com.example.demo.Auth.AuthModel.ApplicationUser;
import com.example.demo.Auth.AuthModel.LoginDto;
import com.example.demo.Auth.AuthModel.PasswordReset;
import com.example.demo.Auth.AuthModel.UserDto;
import com.example.demo.Auth.AuthModel.AuthService.UserService;
import com.example.demo.Auth.Token.TokenGenerator;
import com.example.demo.Service.ConfirmationTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Auth {
    private final UserService userService;
    private final TokenGenerator tokenGenerator;
    private final ConfirmationTokenService confirmationtoken;

    @Autowired
    public Auth(UserService userService, TokenGenerator tokenGenerator, ConfirmationTokenService confirmationtoken) {
        this.userService = userService;
        this.tokenGenerator = tokenGenerator;
        this.confirmationtoken = confirmationtoken;
    }

    // TODO: change path to admin/register
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userdto) {
        ApplicationUser user = userService.saveUser(userdto);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            confirmationtoken.SendToken(user.getName(), user.getEmail(), token);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto login) {
        ApplicationUser user = userService.login(login.getEmail(), login.getPassword());
        String token = tokenGenerator.generateToken(user.getEmail(), user.getRole());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/auth/verifytoken")
    public ResponseEntity<?> reset(@RequestParam String email, @RequestParam String token) {

        // - additional email verififcation checks ? someone write a regex pls
        // this is for angular to grant page access
        Boolean isconfirmed = confirmationtoken.VerifyEmailAndToken(email, token);

        if (isconfirmed) {
            return (ResponseEntity<?>) ResponseEntity.ok();
        }
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/auth/resetpassword")
    public ResponseEntity<?> reset(@RequestBody PasswordReset passwordreset) {

        Boolean isconfirmed = confirmationtoken.VerifyEmailAndToken(passwordreset.email, passwordreset.token);
        if (isconfirmed) {
            Boolean isreset = userService.updatePassword(passwordreset.email, passwordreset.password);

            if (!isreset) {
                return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return (ResponseEntity<?>) ResponseEntity.ok();
        }
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }

}
