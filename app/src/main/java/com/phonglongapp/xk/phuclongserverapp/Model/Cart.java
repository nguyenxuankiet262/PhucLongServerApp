package com.phonglongapp.xk.phuclongserverapp.Model;

public class Cart {
    private int cId;
    private String cName;
    private int cPrice;
    private int cPriceItem;
    private int cQuanity;
    private String cImageCold;
    private String cImageHot;
    private String cStatus;
    private String uId;

    public Cart(){

    }

    public Cart(int cId, String cName, int cPrice, int cPriceItem, int cQuanity, String cImageCold, String cImageHot, String cStatus, String uId) {
        this.cId = cId;
        this.cName = cName;
        this.cPrice = cPrice;
        this.cPriceItem = cPriceItem;
        this.cQuanity = cQuanity;
        this.cImageCold = cImageCold;
        this.cImageHot = cImageHot;
        this.cStatus = cStatus;
        this.uId = uId;
    }

    public String getcImageCold() {
        return cImageCold;
    }

    public void setcImageCold(String cImageCold) {
        this.cImageCold = cImageCold;
    }

    public String getcImageHot() {
        return cImageHot;
    }

    public void setcImageHot(String cImageHot) {
        this.cImageHot = cImageHot;
    }

    public String getcStatus() {
        return cStatus;
    }

    public void setcStatus(String cStatus) {
        this.cStatus = cStatus;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
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
