package com.example.demo.Controller;

import java.util.UUID;

import com.example.demo.Auth.AuthModel.ApplicationUser;
import com.example.demo.Auth.AuthModel.LoginDto;
import com.example.demo.Auth.AuthModel.PasswordReset;
import com.example.demo.Auth.AuthModel.UserDto;
import com.example.demo.Auth.AuthModel.AuthService.UserService;
import com.example.demo.Auth.Token.TokenGenerator;
import com.example.demo.Dto.ResponseStatus;
import com.example.demo.Dto.TokenObj;
import com.example.demo.Service.ConfirmationTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
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

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userdto) {

        try {
            ApplicationUser user = userService.saveUser(userdto);
            String token = UUID.randomUUID().toString();
            confirmationtoken.SendToken(user.getName(), user.getEmail(), token);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
       // return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto login) {
        ApplicationUser user = userService.login(login.getEmail(), login.getPassword());
        String token = tokenGenerator.generateToken(user.getEmail(), user.getRole());
        TokenObj tokendto = new TokenObj(token);
        return ResponseEntity.ok(tokendto);
    }

    @GetMapping("/auth/verifytoken")
    public ResponseEntity<?> reset(@RequestParam String email, @RequestParam String token) {

        // - additional email verififcation checks ? someone write a regex pls
        // this is for angular to grant page access
        try {
            Boolean isconfirmed = confirmationtoken.VerifyEmailAndToken(email, token);
            if(isconfirmed){
                ResponseStatus status = new ResponseStatus("SUCCESS");
                return ResponseEntity.ok(status);    
            }
            else{
                return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            }
           
        } catch (Exception e) {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.FORBIDDEN);
        } 
    }

    @PutMapping("/auth/resetpassword")
    public ResponseEntity<?> reset(@RequestBody PasswordReset passwordreset) {

        Boolean isconfirmed = confirmationtoken.VerifyEmailAndToken(passwordreset.email, passwordreset.token);
        if (isconfirmed) {
            Boolean isreset = userService.updatePassword(passwordreset.email, passwordreset.password);

            if (isreset) {
                ResponseStatus status = new ResponseStatus("SUCCESS");
                return ResponseEntity.ok(status);
            }
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.FORBIDDEN);
    }

}
