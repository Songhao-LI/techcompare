package com.bootcamp.techcompare.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trackings")
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int productId;
    private double currentPrice;
    private String email;

    public Tracking(String email, int productId, double currentPrice) {
        this.email = email;
        this.productId = productId;
        this.currentPrice = currentPrice;
    }

    public Tracking() {

    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
