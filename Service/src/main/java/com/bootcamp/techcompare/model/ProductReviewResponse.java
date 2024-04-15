package com.bootcamp.techcompare.model;

import jakarta.persistence.*;

public class ProductReviewResponse {

    public ProductReviewResponse(int userId, double rate, String comment) {
        this.userId = userId;
        this.rate = rate;
        this.comment = comment;
    }

    private int userId;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
