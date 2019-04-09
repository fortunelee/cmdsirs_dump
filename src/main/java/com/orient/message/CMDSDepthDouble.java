package com.orient.message;

import java.math.BigDecimal;

public class CMDSDepthDouble extends AbstractHandler{


    private String securityID;
    private String symbol;
    private String time;
    private String bidPrice1;
    private String bidVolume1;
    private String bidPrice2;
    private String bidVolume2;
    private String bidPrice3;
    private String bidVolume3;
    private String bidPrice4;
    private String bidVolume4;
    private String bidPrice5;
    private String bidVolume5;
    private String askPrice1;
    private String askVolume1;
    private String askPrice2;
    private String askVolume2;
    private String askPrice3;
    private String askVolume3;
    private String askPrice4;
    private String askVolume4;
    private String askPrice5;
    private String askVolume5;


    public CMDSDepthDouble() {
        super();
    }

    public void setString(String securityID, String symbol, String time) {
        this.securityID = securityID;
        this.symbol = symbol;
        this.time = time;
    }

    public CMDSDepthDouble getCmdsDepthDouble(DepthDto depthDto){
        CMDSDepthDouble cmdsDepthDouble = new CMDSDepthDouble();
        cmdsDepthDouble.setString(depthDto.getSecurityID(),depthDto.getSymbol(),depthDto.getTime());
        if(!depthDto.getBid().isEmpty()){
            for(int i = 0; i < depthDto.getBid().size();i++){
                setBidParams(i,depthDto.getBid().get(i).getPx(),depthDto.getBid().get(i).getQty(),cmdsDepthDouble);
        }
        }
        if(!depthDto.getAsk().isEmpty()){
            for(int i = 0; i < depthDto.getAsk().size();i++){
                setAskParams(i,depthDto.getAsk().get(i).getPx(),depthDto.getAsk().get(i).getQty(),cmdsDepthDouble);
            }
        }
        return cmdsDepthDouble;
    }

    private void setAskParams(int i, String px, String qty, CMDSDepthDouble cmdsDepthDouble) {
        if(Integer.parseInt(BigDecimal.ZERO.toString()) == i){
            cmdsDepthDouble.setAskPrice1(px);
            cmdsDepthDouble.setAskVolume1(qty);
        }
        if(Integer.parseInt(BigDecimal.ONE.toString()) == i){
            cmdsDepthDouble.setAskPrice2(px);
            cmdsDepthDouble.setAskVolume2(qty);
        }
        if(Integer.parseInt(new BigDecimal(2).toString()) == i){
            cmdsDepthDouble.setAskPrice3(px);
            cmdsDepthDouble.setAskVolume3(qty);
        }
        if(Integer.parseInt(new BigDecimal(3).toString()) == i){
            cmdsDepthDouble.setAskPrice4(px);
            cmdsDepthDouble.setAskVolume4(qty);
        }
        if(Integer.parseInt(new BigDecimal(4).toString()) == i){
            cmdsDepthDouble.setAskPrice5(px);
            cmdsDepthDouble.setAskVolume5(qty);
        }
    }

    private void setBidParams(int i, String px, String qty, CMDSDepthDouble cmdsDepthDouble) {

        if(Integer.parseInt(BigDecimal.ZERO.toString()) == i){
            cmdsDepthDouble.setBidPrice1(px);
            cmdsDepthDouble.setBidVolume1(qty);
        }
        if(Integer.parseInt(BigDecimal.ONE.toString()) == i){
            cmdsDepthDouble.setBidPrice2(px);
            cmdsDepthDouble.setBidVolume2(qty);
        }
        if(Integer.parseInt(new BigDecimal(2).toString()) == i){
            cmdsDepthDouble.setBidPrice3(px);
            cmdsDepthDouble.setBidVolume3(qty);
        }
        if(Integer.parseInt(new BigDecimal(3).toString()) == i){
            cmdsDepthDouble.setBidPrice4(px);
            cmdsDepthDouble.setBidVolume4(qty);
        }
        if(Integer.parseInt(new BigDecimal(4).toString()) == i){
            cmdsDepthDouble.setBidPrice5(px);
            cmdsDepthDouble.setBidVolume5(qty);
        }
    }

    @Override
    public String[] getParams() {
        String[] params = new String[23];

        int i = 0;
        params[i++] = securityID;
        params[i++] = symbol;
        params[i++] = time;
        params[i++] = bidPrice1;
        params[i++] = bidVolume1;
        params[i++] = bidPrice2;
        params[i++] = bidVolume2;
        params[i++] = bidPrice3;
        params[i++] = bidVolume3;
        params[i++] = bidPrice4;
        params[i++] = bidVolume4;
        params[i++] = bidPrice5;
        params[i++] = bidVolume5;
        params[i++] =  askPrice1;
        params[i++] =  askVolume1;
        params[i++] =  askPrice2;
        params[i++] =  askVolume2;
        params[i++] =  askPrice3;
        params[i++] =  askVolume3;
        params[i++] =  askPrice4;
        params[i++] =  askVolume4;
        params[i++] =  askPrice5;
        params[i++] =  askVolume5;
        return params;

    }

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

    public String getBidPrice1() {
        return bidPrice1;
    }

    public void setBidPrice1(String bidPrice1) {
        this.bidPrice1 = bidPrice1;
    }

    public String getBidVolume1() {
        return bidVolume1;
    }

    public void setBidVolume1(String bidVolume1) {
        this.bidVolume1 = bidVolume1;
    }

    public String getBidPrice2() {
        return bidPrice2;
    }

    public void setBidPrice2(String bidPrice2) {
        this.bidPrice2 = bidPrice2;
    }

    public String getBidVolume2() {
        return bidVolume2;
    }

    public void setBidVolume2(String bidVolume2) {
        this.bidVolume2 = bidVolume2;
    }

    public String getBidPrice3() {
        return bidPrice3;
    }

    public void setBidPrice3(String bidPrice3) {
        this.bidPrice3 = bidPrice3;
    }

    public String getBidVolume3() {
        return bidVolume3;
    }

    public void setBidVolume3(String bidVolume3) {
        this.bidVolume3 = bidVolume3;
    }

    public String getBidPrice4() {
        return bidPrice4;
    }

    public void setBidPrice4(String bidPrice4) {
        this.bidPrice4 = bidPrice4;
    }

    public String getBidVolume4() {
        return bidVolume4;
    }

    public void setBidVolume4(String bidVolume4) {
        this.bidVolume4 = bidVolume4;
    }

    public String getBidPrice5() {
        return bidPrice5;
    }

    public void setBidPrice5(String bidPrice5) {
        this.bidPrice5 = bidPrice5;
    }

    public String getBidVolume5() {
        return bidVolume5;
    }

    public void setBidVolume5(String bidVolume5) {
        this.bidVolume5 = bidVolume5;
    }

    public String getAskPrice1() {
        return askPrice1;
    }

    public void setAskPrice1(String askPrice1) {
        this.askPrice1 = askPrice1;
    }

    public String getAskVolume1() {
        return askVolume1;
    }

    public void setAskVolume1(String askVolume1) {
        this.askVolume1 = askVolume1;
    }

    public String getAskPrice2() {
        return askPrice2;
    }

    public void setAskPrice2(String askPrice2) {
        this.askPrice2 = askPrice2;
    }

    public String getAskVolume2() {
        return askVolume2;
    }

    public void setAskVolume2(String askVolume2) {
        this.askVolume2 = askVolume2;
    }

    public String getAskPrice3() {
        return askPrice3;
    }

    public void setAskPrice3(String askPrice3) {
        this.askPrice3 = askPrice3;
    }

    public String getAskVolume3() {
        return askVolume3;
    }

    public void setAskVolume3(String askVolume3) {
        this.askVolume3 = askVolume3;
    }

    public String getAskPrice4() {
        return askPrice4;
    }

    public void setAskPrice4(String askPrice4) {
        this.askPrice4 = askPrice4;
    }

    public String getAskVolume4() {
        return askVolume4;
    }

    public void setAskVolume4(String askVolume4) {
        this.askVolume4 = askVolume4;
    }

    public String getAskPrice5() {
        return askPrice5;
    }

    public void setAskPrice5(String askPrice5) {
        this.askPrice5 = askPrice5;
    }

    public String getAskVolume5() {
        return askVolume5;
    }

    public void setAskVolume5(String askVolume5) {
        this.askVolume5 = askVolume5;
    }

    @Override
    public String toString() {
        return "CMDSDepthDouble{" +
                "securityID='" + securityID + '\'' +
                ", symbol='" + symbol + '\'' +
                ", time='" + time + '\'' +
                ", bidPrice1='" + bidPrice1 + '\'' +
                ", bidVolume1='" + bidVolume1 + '\'' +
                ", bidPrice2='" + bidPrice2 + '\'' +
                ", bidVolume2='" + bidVolume2 + '\'' +
                ", bidPrice3='" + bidPrice3 + '\'' +
                ", bidVolume3='" + bidVolume3 + '\'' +
                ", bidPrice4='" + bidPrice4 + '\'' +
                ", bidVolume4='" + bidVolume4 + '\'' +
                ", bidPrice5='" + bidPrice5 + '\'' +
                ", bidVolume5='" + bidVolume5 + '\'' +
                ", askPrice1='" + askPrice1 + '\'' +
                ", askVolume1='" + askVolume1 + '\'' +
                ", askPrice2='" + askPrice2 + '\'' +
                ", askVolume2='" + askVolume2 + '\'' +
                ", askPrice3='" + askPrice3 + '\'' +
                ", askVolume3='" + askVolume3 + '\'' +
                ", askPrice4='" + askPrice4 + '\'' +
                ", askVolume4='" + askVolume4 + '\'' +
                ", askPrice5='" + askPrice5 + '\'' +
                ", askVolume5='" + askVolume5 + '\'' +
                '}';
    }

}
