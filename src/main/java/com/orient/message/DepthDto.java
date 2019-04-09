package com.orient.message;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class DepthDto implements Serializable {

    @JSONField(ordinal = 1)
    private String securityID;
    @JSONField(ordinal = 2)
    private String symbol;
    @JSONField(ordinal = 3)
    private String time;
    @JSONField(ordinal = 4)
    private List<Bid> bid;
    @JSONField(ordinal = 5)
    private List<Ask> ask;

    public String getSecurityID() {
        return securityID;
    }

    public void setSecurityID(String securityID) {
        this.securityID = securityID;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Bid> getBid() {
        return bid;
    }

    public void setBid(List<Bid> bid) {
        this.bid = bid;
    }

    public List<Ask> getAsk() {
        return ask;
    }

    public void setAsk(List<Ask> ask) {
        this.ask = ask;
    }

    @Override
    public String toString() {
        return "DepthDto{" +
                "securityID='" + securityID + '\'' +
                ", symbol='" + symbol + '\'' +
                ", time='" + time + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                '}';
    }
}
