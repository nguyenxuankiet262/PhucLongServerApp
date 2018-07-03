package com.phonglongapp.xk.phuclongserverapp.Model;

public class Drink {
    String id;
    String image_cold;
    String image_hot;
    String menuId;
    String name;
    String price;
    float rating;

    public Drink(){

    }

    public Drink(String id, String image_cold, String image_hot, String menuId, String name, String price,float rating) {
        this.id = id;
        this.image_cold = image_cold;
        this.image_hot = image_hot;
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_cold() {
        return image_cold;
    }

    public void setImage_cold(String image_cold) {
        this.image_cold = image_cold;
    }

    public String getImage_hot() {
        return image_hot;
    }

    public void setImage_hot(String image_hot) {
        this.image_hot = image_hot;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
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
}
