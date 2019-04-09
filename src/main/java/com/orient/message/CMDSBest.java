package com.orient.message;

public class CMDSBest extends AbstractHandler{


    private String securityID = "";

    private String senderCompID = "";

    private String levels = "";

    private String targetCompID = "";

    private String transactTime = "";

    private String bidPrice1 = "";

    private String bidVolume1 = "";

    private String askPrice1 = "";

    private String askVolume1 = "";

    private String bidClear = " ";

    private String askClear = " ";

    public CMDSBest() {

    }

    public CMDSBest(String securityID,String transactTime,String bidClear, String askClear) {
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

        public CMDSBest build() {
            return new CMDSBest(securityID,transactTime,bidClear,askClear);
        }
    }

    public String[] getParams() {
        String[] params = new String[11];

        int i = 0;
        params[i++] = securityID;
        params[i++] = senderCompID;
        params[i++] = levels;
        params[i++] = targetCompID;
        params[i++] = transactTime;
        params[i++] = bidPrice1;
        params[i++] = bidVolume1;
        params[i++] = askPrice1;
        params[i++] = askVolume1;
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
}
