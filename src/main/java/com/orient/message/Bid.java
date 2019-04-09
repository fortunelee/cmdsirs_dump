package com.orient.message;

public class Bid {

    private String px;

    private String qty;


    public String getPx() {
        return px;
    }

    public void setPx(String px) {
        this.px = px;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "px='" + px + '\'' +
                ", qty='" + qty + '\'' +
                '}';
    }
}
