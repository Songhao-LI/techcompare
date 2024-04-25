package com.bootcamp.techcompare.model;

public class StoreProductResponse {

    public int getProductId() {
        return productId;
    }

    public StoreProductResponse(int productId, String stockLevel) {
        this.productId = productId;
        this.stockLevel = stockLevel;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(String stockLevel) {
        this.stockLevel = stockLevel;
    }

    private int productId;

    private String stockLevel;
}
