package com.example.demo.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ConfirmationToken {
    @Id
    private String Email;
    private String Token;

    public ConfirmationToken() {
    }

    public ConfirmationToken(String Email, String Token) {
        this.Email = Email;
        this.Token = Token;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getToken() {
        return this.Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

}
