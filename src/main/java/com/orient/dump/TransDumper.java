package com.orient.dump;

import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.orient.constants.Constants;
import com.orient.message.*;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.orient.client.RequestHandler;
import com.orient.common.ReadConfig;
import com.orient.constants.SqlConstants;

public class TransDumper extends Thread {
	private final static Logger logger = LoggerFactory.getLogger(TransDumper.class);

	private boolean dumpOn = false;

	private BlockingQueue<MsgType> inQueue = null;

	private QueryRunner qr = null;
	private C3P0Utils c3p0Util = null;

	private String insertSQL = null;

	private AbstractHandler abstractHandler = null;

	static{
		String year = CreateTableUtil.getYear();
		String date = CreateTableUtil.getDate();

		Constants.MSG_MAP.forEach((s, s2) -> {

			Constants.MSG_MAP.put(s,String.format(s2,year,date));
		});
	}

	public TransDumper(ReadConfig config, BlockingQueue<MsgType> inQueue) {

		this.inQueue = inQueue;

		String dumpOn = config.getConfigItem("DumpON");
		if (!(dumpOn == null || dumpOn.isEmpty())) {
			if (dumpOn.equalsIgnoreCase("true"))
				this.dumpOn = true;
			else if (dumpOn.equalsIgnoreCase("false")) {
				this.dumpOn = false;
				logger.info("DumpOn is switched off.");
			}
		}

		if (this.dumpOn) {
			try {
				c3p0Util = new C3P0Utils(config);
				qr = new QueryRunner(c3p0Util.getDataSource());

			} catch (Exception e) {
				logger.error("C3P0Util initialized failed and exit.");
				System.exit(2);
			}
		}

	}

	@Override
	public void run() {
		int interval = 1;
		if (!this.dumpOn)
			interval = 10;

		while (true) {
			MsgType msg = null;
			try {
				msg = inQueue.poll(interval, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (msg == null)
				continue;

			processDump(msg);
		}
	}

	private void logParams(String[] params, boolean save) {
		if (save)
			logger.info(String.format("CMDSIRSTrans save ok:%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", (Object[]) params));
		else
			logger.info(String.format("CMDSIRSTrans recv/parse ok:%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", (Object[]) params));
	}

	private boolean processDump(MsgType msg) {
		if (!dumpOn) {
			/////////////////////////////////////////////////////////////////////////////////
			/////////////////////////////For  Debug /////////////////////////////////////////
			String decodedMsg = RequestHandler.decodeMsg(msg.getMsg());
			if (decodedMsg.isEmpty())
				return false;

			CMDSTrans trans = JSON.parseObject(decodedMsg, new TypeReference<CMDSTrans>() {
			});

			String[] params = trans.getParams();
			logParams(params, false);
			/////////////////////////////////////////////////////////////////////////////////
			return true;
		}

		try {

			if (msg.getMsg().equals("") && null == msg){

				logger.info("'"+TransDumper.class.getSimpleName()+"'",new RuntimeException("processDump method params is null"));
				return false;
			}

			logger.info("processDump method params detail , msg type:{} , msg info:{}",
					msg.getMsgType(),msg.getMsg());
			if (Constants.Common.MSGTYPE_TSI == msg.getMsgType()) {

				insertSQL = Constants.MSG_MAP.get(Constants.Common.MSGTYPE_TSI);
				abstractHandler = (CMDSTrans)JSON.parseObject(msg.getMsg(),new TypeReference<CMDSTrans>(){});
				return insertSqltoTable(abstractHandler);

			}else if(Constants.Common.MSGTYPE_MDBI == msg.getMsgType()){

				insertSQL = Constants.MSG_MAP.get(Constants.Common.MSGTYPE_MDBI);
				abstractHandler = (CMDSBest) JSON.parseObject(msg.getMsg(),new TypeReference<CMDSBest>(){});
				return insertSqltoTable(abstractHandler);

			}else if(Constants.Common.MSGTYPE_MDOI == msg.getMsgType()){

				insertSQL = Constants.MSG_MAP.get(Constants.Common.MSGTYPE_MDOI);
				abstractHandler = (CMDSDepthSingle) JSON.parseObject(msg.getMsg(),new TypeReference<CMDSDepthSingle>(){});
				return insertSqltoTable(abstractHandler);

			}else if(Constants.Common.MSGTYPE_MDI == msg.getMsgType()){

				insertSQL = Constants.MSG_MAP.get(Constants.Common.MSGTYPE_MDI);
				CMDSDepthDouble cmdsDepthDouble = getCmdsDepthDouble(msg);
				return insertSqltoTable(cmdsDepthDouble);

			}else if(Constants.Common.MSGTYPE_MDCI == msg.getMsgType()){

				IRSQuoteCommand irsQuoteCommand = JSON.parseObject(msg.getMsg(), new TypeReference<IRSQuoteCommand>(){});
				abstractHandler = getDepthSource(irsQuoteCommand);
				return insertSqltoTable(abstractHandler);
			}

		} catch (Throwable throwable) {

			throwable.printStackTrace();
			logger.error("insert sql to table fail,params detail：【sql template is :{}," +
					"'"+abstractHandler.getClass().getSimpleName()+"' params array is :{}】",
						insertSQL,abstractHandler.getParams());
		}
		return true;
	}

	private boolean insertSqltoTable(AbstractHandler abs) throws SQLException {

		String[] params = abs.getParams();
		int ret = qr.execute(insertSQL, (Object[]) params);
		if (ret == 1) {
			logParams(params, true);
			return true;
		} else {
			logger.error(String.format("CMDSIRS PARAMS save failed:%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", (Object[]) params));
			return false;
		}
	}

	private CMDSDepthDouble getCmdsDepthDouble(MsgType msg) {

		DepthDto depthDto = (DepthDto) JSON.parseObject(msg.getMsg(),new TypeReference<DepthDto>(){});
		logger.info("depthDto to json after is :{}",JSON.toJSON(depthDto));
		CMDSDepthDouble cmdsDepthDouble = new CMDSDepthDouble().getCmdsDepthDouble(depthDto);
		return cmdsDepthDouble;
	}

	private AbstractHandler getDepthSource(IRSQuoteCommand irsQuoteCommand) {


			if (null == irsQuoteCommand && " " == irsQuoteCommand.toJsonString()) {

				logger.info("'"+TransDumper.class.getSimpleName()+"'",new RuntimeException("getDepthSource method params is null"));
			} else {

				try {

					logger.info("getDepthSource method params detail , isbest:{} , irsQuoteCommand:{}",
							String.valueOf(irsQuoteCommand.getBest()),JSON.toJSONString(irsQuoteCommand));
				if (irsQuoteCommand.getBest()) {
					abstractHandler = new CMDSBest().builder().securityID(irsQuoteCommand.getSecurityID())
							.transactTime(irsQuoteCommand.getTransactTime())
							.bidClear(Boolean.toString(irsQuoteCommand.isBidClear()))
							.askClear(Boolean.toString(irsQuoteCommand.isAskClear())).build();
					insertSQL = Constants.MSG_MAP.get(Constants.Common.MSGTYPE_MDBI);
					return abstractHandler;
				} else {
					abstractHandler = new CMDSDepthSingle().builder().securityID(irsQuoteCommand.getSecurityID())
							.transactTime(irsQuoteCommand.getTransactTime())
							.bidClear(Boolean.toString(irsQuoteCommand.isBidClear()))
							.askClear(Boolean.toString(irsQuoteCommand.isAskClear())).build();
					insertSQL = Constants.MSG_MAP.get(Constants.Common.MSGTYPE_MDOI);
					return abstractHandler;
				}
				}catch (Throwable throwable){

					throwable.printStackTrace();
					logger.error("get depthsource fail,params detail ：【sql template is :{}," +
									"'"+abstractHandler.getClass().getSimpleName()+ "' params array is :{}】",
							insertSQL,abstractHandler.getParams());
				}
			}
		return null;
	}
}