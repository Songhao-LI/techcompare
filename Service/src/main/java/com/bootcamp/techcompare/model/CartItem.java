package com.bootcamp.techcompare.model;

import jakarta.persistence.*;
import software.amazon.awssdk.services.cognitoidentityprovider.endpoints.internal.Value;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private int productId;

    public CartItem(String username, int productId, int quantity) {
        this.username = username;
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartItem() {

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;
}
