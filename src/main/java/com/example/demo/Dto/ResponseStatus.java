package com.example.demo.Dto;

public class ResponseStatus {

    public ResponseStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
