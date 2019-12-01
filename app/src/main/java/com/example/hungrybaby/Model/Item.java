package com.example.hungrybaby.Model;

public class Item {

    private String itemId;
    private String itemName;
    private String price;
//    private String image;

    public Item() {
    }

    public Item(String itemId, String itemName, String price/*, String image*/) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
//        this.image = image;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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
