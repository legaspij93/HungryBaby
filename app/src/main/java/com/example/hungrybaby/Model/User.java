package com.example.hungrybaby.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    private String userId;
    private String name;
    private String email;
    private String contactNum;
    private String password;
    private String address;

    public User(String name, String email, String number, String address){
        this.name = name;
        this.email = email;
        this.contactNum = number;
        this.address = address;
    }

    public User(String userId, String name, String email, String contactNum, String password, String address){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contactNum = contactNum;
        this.password = password;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNum() {
        return contactNum;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }
}
