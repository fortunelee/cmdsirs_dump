package com.orient.fixd;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.annotation.JSONField;
import com.orient.utils.StringUtils;

public class SubscribeRequest extends ISubPacket {

	@JSONField(name="262")
	private String requestID = "";
	
	@JSONField(name="207")
	private String securityExchange;
	
	@JSONField(name="207BL")
	private List<String> bonds = null;
	
	// subscribe all market
	// securityExchange must equals to "BOND"
	public SubscribeRequest(String requestID, String securityExchange)
	{
		this.requestID = requestID;
		this.securityExchange = securityExchange;
	}
	
	// subscribe bond market with bonds list.
	// securityExchange must equals to "BOND"
	public SubscribeRequest(String requestID, String securityExchange, List<String> bonds)
	{
		this(requestID, securityExchange);
		
		if (bonds == null)
			throw new RuntimeException("Subscribe bonds empty.");
		
		this.bonds = bonds;
	}
	
	public List<String> getBonds()
	{
		return bonds;
	}
	
	public String getSecurityExchange()
	{
		return securityExchange;
	}
	
	public String getRequestID()
	{
		return requestID;
	}
	
	@Override
	public String subPacketToString() {
		StringBuilder b = new StringBuilder(8192);
		
		if (isJsonEncoded())
		{
			if (requestID != null && !requestID.isEmpty())
				b.append("262=").append(requestID).append('|');
			
			String encoded = jsonGZIPBase64Encode(joinToJson(bonds, securityExchange));
			//String lenStr = "354=" + encoded.length();
			
			//return b.append("355=").append(encoded).toString();
			return encoded;
		}
		else
		{
			if (requestID != null && !requestID.isEmpty())
				b.append("262=").append(requestID).append('|');
			b.append("207=").append(securityExchange);
			return b.toString();
		}
	}

	@Override
	public String msgType() {
		return "V";
	}

	@Override
	public boolean isJsonEncoded() {
		if (bonds != null && !bonds.isEmpty())
			return true;
		return false;
	}

	public static boolean parseListString(String str, List<String> parsedList)
	{
		if (parsedList == null)
			return false;
		
		String []strSegments = str.split(",");
		for (int i = 0; i < strSegments.length; i++)
		{
			int indexFrom = strSegments[i].indexOf("\"") + 1;
			int indexTo   = strSegments[i].lastIndexOf("\"");
			parsedList.add(strSegments[i].substring(indexFrom, indexTo));
		}
		
		return true;
	}
	
	private static String addDoubleQuotedChar(String str)
	{
		return "\"" + str + "\"";
	}
	
	private static String joinToJson(List<String> bonds, String market)
	{
		List<String> bondsQuoted = bonds.stream().map(SubscribeRequest::addDoubleQuotedChar).collect(Collectors.toList());
		
		//return String.format("{\"%s\":[%s]}", market, StringUtils.join(bondsQuoted, ","));
		
		return "{\"" + market + "\":[" + StringUtils.join(bondsQuoted, ",") + "]}";
	}
	
	private static void test()
	{
		List<String> bonds = new ArrayList<>();
		
		bonds.add("cd12");
		bonds.add("ce13");
		bonds.add("cf14");
		bonds.add("cg15");
		System.out.println(joinToJson(bonds, "BOND"));
	}
	
	private static void test2()
	{
		List<String> bonds = new ArrayList<>();
		
		bonds.add("cd12");
		bonds.add("ce13");
		bonds.add("cf14");
		bonds.add("cg15");
		SubscribeRequest sr = new SubscribeRequest("", "BOND", bonds);
		System.out.println(sr.subPacketToString());
	}
	
	private static void test3()
	{
		SubscribeRequest sr = new SubscribeRequest("", "BOND");
		System.out.println(sr.subPacketToString());
	}
	
	public static void main(String[] args) {
		test2();
		
		String str = "H4sIAAAAAAAAAKtWcvL3c1GyilZKTjE0UtJRSk41NAZRaYYmICrd0FQpthYABj9DMSYAAAA!";
		System.out.println(str.length());
		
		System.out.println("-------------------------------");
		
		test3();
		
		System.out.println("----------------------------------");
		
		String json = "\"cd12\",\"ce13\",\"cf14\",\"cg15\"";
		List<String> jsonList = new ArrayList<>();
		parseListString(json, jsonList);
		jsonList.forEach((s) -> System.out.println(s));
	}
}
