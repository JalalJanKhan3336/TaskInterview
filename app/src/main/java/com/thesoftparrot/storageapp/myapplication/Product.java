package com.thesoftparrot.storageapp.myapplication;

import java.io.Serializable;

public class Product implements Serializable {
    private String productId, productName;
    private String quantity;

    public Product() {}

    public Product(String productId, String productName, String quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
