package com.orient.message;

public class CMDSDepthSingle extends AbstractHandler{


    private String securityID;
    private String senderCompID;
    private String levels;
    private String targetCompID;
    private String transactTime;
    private String bidPrice1 = " ";
    private String bidVolume1 = " ";
    private String bidPrice2 = " ";
    private String bidVolume2 = " ";
    private String bidPrice3 = " ";
    private String bidVolume3 = " ";
    private String bidPrice4 = " ";
    private String bidVolume4 = " ";
    private String bidPrice5 = " ";
    private String bidVolume5 = " ";
    private String askPrice1 = " ";
    private String askVolume1 = " ";
    private String askPrice2 = " ";
    private String askVolume2 = " ";
    private String askPrice3 = " ";
    private String askVolume3 = " ";
    private String askPrice4 = " ";
    private String askVolume4 = " ";
    private String askPrice5 = " ";
    private String askVolume5 = " ";
    private String bidClear = " ";
    private String askClear = " ";

    public CMDSDepthSingle() {

    }

    public CMDSDepthSingle(String securityID, String transactTime, String bidClear, String askClear) {
        this.securityID = securityID;
        this.transactTime = transactTime;
        this.bidClear = bidClear;
        this.askClear = askClear;
    }


    public  Builder builder() {
        return new Builder();
    }

    public static class Builder{

        private String securityID;

        private String transactTime;

        private String bidClear;

        private String askClear;

        public Builder securityID(final String securityID) {
            this.securityID = securityID;
            return this;
        }

        public Builder transactTime(final String transactTime) {
            this.transactTime = transactTime;
            return this;
        }

        public Builder bidClear(final String bidClear) {
            this.bidClear = bidClear;
            return this;
        }

        public Builder askClear(final String askClear) {
            this.askClear = askClear;
            return this;
        }

        public CMDSDepthSingle build() {
            return new CMDSDepthSingle(securityID,transactTime,bidClear,askClear);
        }
    }

    @Override
    public String[] getParams() {

        String[] params = new String[27];

        int i = 0;
        params[i++] = securityID;
        params[i++] = senderCompID;
        params[i++] = levels;
        params[i++] = targetCompID;
        params[i++] = transactTime;
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
        params[i++] = askPrice1;
        params[i++] = askVolume1;
        params[i++] = askPrice2;
        params[i++] = askVolume2;
        params[i++] = askPrice3;
        params[i++] = askVolume3;
        params[i++] = askPrice4;
        params[i++] = askVolume4;
        params[i++] = askPrice5;
        params[i++] = askVolume5;
        params[i++] = bidClear;
        params[i++] = askClear;
        return params;

    }



    public String getSecurityID() {
        return securityID;
    }

    public void setSecurityID(String securityID) {
        this.securityID = securityID;
    }

    public String getSenderCompID() {
        return senderCompID;
    }

    public void setSenderCompID(String senderCompID) {
        this.senderCompID = senderCompID;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public String getTargetCompID() {
        return targetCompID;
    }

    public void setTargetCompID(String targetCompID) {
        this.targetCompID = targetCompID;
    }

    public String getTransactTime() {
        return transactTime;
    }

    public void setTransactTime(String transactTime) {
        this.transactTime = transactTime;
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

    public String getBidClear() {
        return bidClear;
    }

    public void setBidClear(String bidClear) {
        this.bidClear = bidClear;
    }

    public String getAskClear() {
        return askClear;
    }

    public void setAskClear(String askClear) {
        this.askClear = askClear;
    }

    @Override
    public String toString() {
        return "CMDSDepthSingle{" +
                "securityID='" + securityID + '\'' +
                ", senderCompID='" + senderCompID + '\'' +
                ", levels='" + levels + '\'' +
                ", targetCompID='" + targetCompID + '\'' +
                ", transactTime='" + transactTime + '\'' +
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
                ", bidClear='" + bidClear + '\'' +
                ", askClear='" + askClear + '\'' +
                '}';
    }
}
