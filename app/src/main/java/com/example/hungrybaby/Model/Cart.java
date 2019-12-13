package com.example.hungrybaby.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Cart {
    private String order;
    private String cost;
    private int quantity;

    public Cart(){

    }

    public Cart(String order, String cost){
        this.order = order;
        this.cost = cost;
        quantity = 1;
    }

    public String getOrder() {
        return order;
    }

    public String getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
