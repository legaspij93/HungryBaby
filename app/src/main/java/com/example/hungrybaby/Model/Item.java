package com.example.hungrybaby.Model;

public class Item {

    private String itemId;
    private String name;
    private String price;
//    private String image;

    public Item() {
    }

    public Item(String itemId, String name, String price/*, String image*/) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
//        this.image = image;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
}
