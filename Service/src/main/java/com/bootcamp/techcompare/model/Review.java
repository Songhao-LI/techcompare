package com.bootcamp.techcompare.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Review(String username, int productId, double rate, String comment) {
        this.username = username;
        this.productId = productId;
        this.rate = rate;
        this.comment = comment;
    }

    public Review() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

//    private int userId;
    private String username;

    private int productId;

    private double rate;

    private String comment;

}
