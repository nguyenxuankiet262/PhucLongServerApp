package com.phonglongapp.xk.phuclongserverapp.Model;

public class Cart {
    private int cId;
    private String cName;
    private int cPrice;
    private int cPriceItem;
    private int cQuanity;

    public Cart(){

    }

    public Cart(int cId, String cName, int cPrice, int cPriceItem, int cQuanity) {
        this.cId = cId;
        this.cName = cName;
        this.cPrice = cPrice;
        this.cPriceItem = cPriceItem;
        this.cQuanity = cQuanity;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public int getcPrice() {
        return cPrice;
    }

    public void setcPrice(int cPrice) {
        this.cPrice = cPrice;
    }

    public int getcPriceItem() {
        return cPriceItem;
    }

    public void setcPriceItem(int cPriceItem) {
        this.cPriceItem = cPriceItem;
    }

    public int getcQuanity() {
        return cQuanity;
    }

    public void setcQuanity(int cQuanity) {
        this.cQuanity = cQuanity;
    }
}
