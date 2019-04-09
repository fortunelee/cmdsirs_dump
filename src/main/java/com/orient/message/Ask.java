package com.orient.message;

public class Ask extends Thread{


    private String px;

    private String qty;

    private Ask(){

    }

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
        return "Ask{" +
                "px='" + px + '\'' +
                ", qty='" + qty + '\'' +
                '}';
    }
}
