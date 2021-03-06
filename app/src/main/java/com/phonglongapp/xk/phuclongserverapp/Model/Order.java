package com.phonglongapp.xk.phuclongserverapp.Model;

import java.util.List;

public class Order {
    private String id;
    private String name;
    private String address;
    private String phone;
    private String price;
    private String status;
    private String note;
    private String payment;
    private String userID;
    List<Cart> cartList;

    public Order(){

    }

    public Order(String id, String name, String address, String phone, String price, String status, String note, String payment, String userID, List<Cart> cartList) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.price = price;
        this.status = status;
        this.note = note;
        this.payment = payment;
        this.userID = userID;
        this.cartList = cartList;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }
}
