package com.bootcamp.techcompare.model;

public class UserTrackerResponse {

    private int productId;

    private double currentPrice;

    public double getTargetPrice() {
        return targetPrice;
    }

    public UserTrackerResponse(int productId, double currentPrice, double targetPrice) {
        this.productId = productId;
        this.currentPrice = currentPrice;
        this.targetPrice = targetPrice;
    }

    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    private double targetPrice;

}
