package com.orient.message;

public class CMDSTrans extends AbstractHandler{
	private String securityID;
	
	private String senderCompID;
	
	private String targetCompID;
	
	private String transactTime;
	
	private String latestRate;
	
	private String tradeVolume;
	
	private String openingRate;
	
	private String highestRate;
	
	private String lowestRate;
	
	private String intradayPrice;
	
	private String totalVolume;

	public String[] getParams() {
		String[] params = new String[11];
		
		int i = 0;
		params[i++] = securityID;
		params[i++] = senderCompID;
		params[i++] = targetCompID;
		params[i++] = transactTime;
		params[i++] = latestRate;
		params[i++] = tradeVolume;
		params[i++] = openingRate;
		params[i++] = highestRate;
		params[i++] = lowestRate;
		params[i++] = intradayPrice;
		params[i++] = totalVolume;
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

	public String getTargetCompID() {
		return targetCompID;
	}

	public void setTargetCompID(String tragetCompID) {
		this.targetCompID = tragetCompID;
	}

	public String getTransactTime() {
		return transactTime;
	}

	public void setTransactTime(String transactTime) {
		this.transactTime = transactTime;
	}

	public String getLatestRate() {
		return latestRate;
	}

	public void setLatestRate(String latestRate) {
		this.latestRate = latestRate;
	}

	public String getTradeVolume() {
		return tradeVolume;
	}

	public void setTradeVolume(String tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

	public String getOpeningRate() {
		return openingRate;
	}

	public void setOpeningRate(String openingRate) {
		this.openingRate = openingRate;
	}

	public String getHighestRate() {
		return highestRate;
	}

	public void setHighestRate(String highestRate) {
		this.highestRate = highestRate;
	}

	public String getLowestRate() {
		return lowestRate;
	}

	public void setLowestRate(String lowestRate) {
		this.lowestRate = lowestRate;
	}

	public String getIntradayPrice() {
		return intradayPrice;
	}

	public void setIntradayPrice(String intradayPrice) {
		this.intradayPrice = intradayPrice;
	}

	public String getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(String totalVolume) {
		this.totalVolume = totalVolume;
	}
}
