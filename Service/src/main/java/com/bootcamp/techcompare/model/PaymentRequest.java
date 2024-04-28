package com.bootcamp.techcompare.model;

public class PaymentRequest {

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public PaymentRequest(String username, String userEmail) {
        this.username = username;
        this.userEmail = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    private String userEmail;
}
