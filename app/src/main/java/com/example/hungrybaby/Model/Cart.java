package com.example.hungrybaby.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Cart {
    private String order;
    private String cost;

    public Cart(){

    }

    public Cart(String order, String totalCost){
        this.order = order;
        this.cost = totalCost;
    }

    public String getOrder() {
        return order;
    }

    public String getTotalCost() {
        return cost;
    }
}
