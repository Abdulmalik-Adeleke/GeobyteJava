package com.example.demo.Auth.AuthModel;

public class UserDto {
    private String name;
    private String email;
    private String role;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return "ROLE_" + this.role.toUpperCase();
    }

    public void setRole(String role) {
        this.role = role;
    }

}
