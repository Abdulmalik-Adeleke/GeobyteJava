package com.example.demo.Dto;

public class TokenObj {
    private String token;

    public TokenObj(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

   
    @Override
    public String toString() {
        return "{" +
            " token='" + getToken() + "'" +
            "}";
    }
}
