package com.bootcamp.techcompare.model;

import jakarta.persistence.*;

public class ProductReviewResponse {

    public ProductReviewResponse(String username, double rate, String comment) {
        this.username = username;
        this.rate = rate;
        this.comment = comment;
    }

    private String username;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private double rate;

    private String comment;

}
