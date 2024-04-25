package com.bootcamp.techcompare.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trackers")
public class Tracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;
    private int productId;
    private double targetPrice;
//    private String email;

    public Tracker(int userId, int productId, double targetPrice) {
        this.userId = userId;
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
