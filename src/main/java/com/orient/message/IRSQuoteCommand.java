package com.orient.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.orient.fixd.ISubPacket;

import java.io.Serializable;

public class IRSQuoteCommand extends ISubPacket implements Serializable {
	private String securityID = "";
	
	private boolean isNeedClear = false;
	private boolean isBest;     // 是否是最优行情
	
	private int bidOrAsk = 0;

	private boolean askClear;

	private boolean best;

	private boolean bidClear;

	private boolean jsonEncoded;

	private String transactTime = "";
	
	@JSONField(serialize=false)
	public static final int ASK = 1;
	@JSONField(serialize=false)
	public static final int BID = 2;


	public IRSQuoteCommand(String securityID,boolean askClear, boolean best, boolean bidClear, boolean jsonEncoded, String transactTime) {
		this.securityID = securityID;
		this.askClear = askClear;
		this.best = best;
		this.bidClear = bidClear;
		this.jsonEncoded = jsonEncoded;
		this.transactTime = transactTime;
	}

	public IRSQuoteCommand(String securityID, String transactTime, int bidOrAsk, boolean isBest)
	{
		this.securityID = securityID;
		this.transactTime = transactTime;
		this.isNeedClear = true;
		this.isBest = isBest;
		this.best = isBest;
		if (bidOrAsk == ASK || bidOrAsk == BID)
			this.bidOrAsk = bidOrAsk;
		else
			throw new RuntimeException(String.format("bidOrAsk = %d [should = 1(ASK), = 2(BID)]", bidOrAsk));
	}

	public boolean isBest() {
		return isBest;
	}

	public boolean isBidClear()
	{
		if (this.bidOrAsk == BID && isNeedClear)
			return true;
		return false;
	}
	
	public boolean isAskClear()
	{
		if (this.bidOrAsk == ASK && isNeedClear)
			return true;
		return false;
	}
	
	public String getSecurityID()
	{
		return securityID;
	}
	
	public String getTransactTime()
	{
		return transactTime;
	}

	public void setAskClear(boolean askClear) {
		this.askClear = askClear;
	}

	public void setBest(boolean best) {
		this.best = best;
	}

	public boolean getBest() {

		return best;
	}

	public void setBidClear() {
		this.bidClear = bidClear;
	}

	public void setJsonEncoded(boolean jsonEncoded) {
		this.jsonEncoded = jsonEncoded;
	}


	@Override
	public String toString() {
		if (isAskClear())
		{
			if (isBest())
				return securityID + "[Best]:" + transactTime + ":askClear";
			else
				return securityID + "[Depth]:" + transactTime + ":askClear";
		}
		else if (isBidClear())
		{
			if (isBest())
				return securityID + "[Best]:" + transactTime + ":bidClear";
			else
				return securityID + "[Depth]:" + transactTime + ":bidClear";
		}
		else
		{
			return "";
		}
	}

	public String toJsonString()
	{
		return JSON.toJSONString(this);
	}
	
	@Override
	public String subPacketToString() {
		return jsonGZIPBase64Encode(toJsonString());
	}

	@Override
	public String msgType() {
		return "MDCI";   // MarketDepthCommandInterest(RateSwap)
	}

	@Override
	public boolean isJsonEncoded() {
		return true;
	}


}
