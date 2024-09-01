package com.example.banksystemapi.Responses;

public class LoginResponse {
    private String status;
    private String message;

    public LoginResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }
    public void setMessage() {
        this.message = message;
    }
}