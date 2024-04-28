package com.bootcamp.techcompare.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trackers")
public class Tracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    private int productId;
    private double targetPrice;
//    private String email;

    public Tracker(String username, int productId, double targetPrice) {
        this.username = username;
        this.productId = productId;
        this.targetPrice = targetPrice;
    }

    public Tracker() {

    }

    public double getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(double currentPrice) {
        this.targetPrice = currentPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

}
