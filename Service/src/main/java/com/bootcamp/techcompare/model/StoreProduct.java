package com.bootcamp.techcompare.model;

import jakarta.persistence.*;

@Entity
@Table(name = "store_products")
public class StoreProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public StoreProduct() {

    }

    public int getStoreId() {
        return storeId;
    }

    public StoreProduct(int storeId, int productId) {
        this.storeId = storeId;
        this.productId = productId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    private int storeId;

    private int productId;
}
