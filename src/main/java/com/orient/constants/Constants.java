package com.orient.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Constants {
	public static class Common {
		public static final int MAX_PACKET_LENGTH = 256 * 1024;
		
		public static final String BOND_MARKET = "BOND";

		public static final String MSGTYPE_WELCOME = "WELCOME";

		public static final String MSGTYPE_MD = "MD";

		public static final String MSGTYPE_TS = "TS";

		public static final String MSGTYPE_MDF = "MDF";

		public static final String MSGTYPE_TSF = "TSF";

		public static final String MSGTYPE_0 = "0";

		public static final String MSGTYPE_A = "A";

		public static final String MSGTYPE_V = "V";

		public static final String MSGTYPE_MDI = "MDI";

		public static final String MSGTYPE_MDBI = "MDBI";

		public static final String MSGTYPE_TSI = "TSI";

		public static final String MSGTYPE_STF = "STF";

		public static final String MSGTYPE_MDOI = "MDOI";

		public static final String MSGTYPE_MDCI = "MDCI";

	}


	public static Map<String, String> MSG_MAP = new HashMap(20){
		{
			put("TSI",SqlConstants.InsertSqlTemplate);
			put("MDBI",SqlConstants.InsertBestSqlTemplate);
			put("MDOI",SqlConstants.InsertDepthSingleqlTemplate);
			put("MDI",SqlConstants.InsertDepthDoublesqlTemplate);
		}
	};



}
