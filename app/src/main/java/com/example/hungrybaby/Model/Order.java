package com.example.hungrybaby.Model;

import java.util.List;

public class Order {
    private String orderId;
    private List<Cart> orders;
    private String totalCost;
    private String deliverAddress;
    private String user;

    public Order(){

    }

    public Order(String orderId, List<Cart> orders, String totalCost, String deliverAddress, String user){
        this.orderId = orderId;
        this.orders = orders;
        this.totalCost = totalCost;
        this.deliverAddress = deliverAddress;
        this.user = user;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<Cart> getOrders() {
        return orders;
    }

    public void setOrders(List<Cart> orders) {
        this.orders = orders;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

