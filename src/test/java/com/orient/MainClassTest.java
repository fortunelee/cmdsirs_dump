package com.orient;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.alibaba.fastjson.JSON;
import com.orient.client.NettyClient;
import com.orient.message.IRSQuoteCommand;
import com.orient.message.MsgType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.orient.common.ReadConfig;
import com.orient.dump.TransDumper;

public class MainClassTest {



    ReadConfig config = null;

    MsgType msgType = null;

    BlockingQueue<MsgType> jsonQueue = null;



        @Before
        public void before(){

            config = new ReadConfig();
            jsonQueue = new LinkedBlockingQueue<MsgType>();
            if (!config.loadConfig("cfg/server.cfg"))
            {
                return;
            }

        }



        @Test
        public void transTest(){

            msgType = new MsgType("TSI"," {\n" +
                    "\t\"securityID\": \"FR007_3Y\",\n" +
                    "\t\"senderCompID\": \"CFETS-RMB-CMDS\",\n" +
                    "\t\"targetCompID\": \"100063\",\n" +
                    "\t\"transactTime\": \"20190401-16:49:14.130\",\n" +
                    "\t\"latestRate\": \"2.8300\",\n" +
                    "\t\"tradeVolume\": \"50000000\",\n" +
                    "\t\"openingRate\": \"2.7950\",\n" +
                    "\t\"highestRate\": \"2.8300\",\n" +
                    "\t\"lowestRate\": \"2.7950\",\n" +
                    "\t\"intradayPrice\": \"2.8175\",\n" +
                    "\t\"totalVolume\": \"300000000\"\n" +
                    "}");
        }


        @Test
        public void bestTest(){

            msgType = new MsgType("MDBI"," {\n" +
                    "\t\"securityID\": \"CDB10_3M\",\n" +
                    "\t\"senderCompID\": \"CFETS-RMB-CMDS\",\n" +
                    "\t\"levels\": 1,\n" +
                    "\t\"targetCompID\": \"100063\",\n" +
                    "\t\"transactTime\": \"20190401-16:49:11.850\",\n" +
                    "\t\"bidPrice1\": \"3.5100\",\n" +
                    "\t\"bidVolume1\": \"650000000\",\n" +
                    "\t\"askPrice1\": \"\",\n" +
                    "\t\"askVolume1\": \"\"\n" +
                    "}");

        }


        @Test
        public void  depthSignleTest(){

            msgType = new MsgType("MDOI","{\n" +
                    "\"securityID\": \"FDR007_5Y\",\n" +
                    "\"senderCompID\": \"CFETS-RMB-CMDS\",\n" +
                    "\"levels\": 4,\n" +
                    "\"targetCompID\": \"100063\",\n" +
                    "\"transactTime\": \"20190402-14:38:26.930\",\n" +
                    "\"bidPrice1\": \"\",\n" +
                    "\"bidVolume1\": \"\",\n" +
                    "\"bidPrice2\": \"\",\n" +
                    "\"bidVolume2\": \"\",\n" +
                    "\"bidPrice3\": \"\",\n" +
                    "\"bidVolume3\": \"\",\n" +
                    "\"bidPrice4\": \"\",\n" +
                    "\"bidVolume4\": \"\",\n" +
                    "\"bidPrice5\": \"\",\n" +
                    "\"bidVolume5\": \"\",\n" +
                    "\"askPrice1\": \"3.0950\",\n" +
                    "\"askVolume1\": \"40000000\",\n" +
                    "\"askPrice2\": \"3.1000\",\n" +
                    "\"askVolume2\": \"80000000\",\n" +
                    "\"askPrice3\": \"3.5000\",\n" +
                    "\"askVolume3\": \"40000000\",\n" +
                    "\"askPrice4\": \"3.9000\",\n" +
                    "\"askVolume4\": \"40000000\",\n" +
                    "\"askPrice5\": \"\",\n" +
                    "\"askVolume5\": \"\"\n" +
                    "}");
        }


        @Test
        public void  depthDoubleTest(){

            msgType = new MsgType("MDI","{\n" +
                    "\"securityID\": \"FR007_5Y\",\n" +
                    "\"symbol\": \"\",\n" +
                    "\"time\": \"2019-04-01 16:52:18.631\",\n" +
                    "\"bid\": [{\n" +
                    "\t\"px\": 3.0200,\n" +
                    "\t\"qty\": 80000000.0000\n" +
                    "}, {\n" +
                    "\t\"px\": 3.0175,\n" +
                    "\t\"qty\": 100000000.0000\n" +
                    "}, {\n" +
                    "\t\"px\": 3.0150,\n" +
                    "\t\"qty\": 100000000.0000\n" +
                    "}, {\n" +
                    "\t\"px\": 3.0125,\n" +
                    "\t\"qty\": 20000000.0000\n" +
                    "}, {\n" +
                    "\t\"px\": 3.0100,\n" +
                    "\t\"qty\": 220000000.0000\n" +
                    "}],\n" +
                    "\"ask\": [{\n" +
                    "\t\"px\": 3.0225,\n" +
                    "\t\"qty\": 40000000.0000\n" +
                    "}, {\n" +
                    "\t\"px\": 3.0250,\n" +
                    "\t\"qty\": 140000000.0000\n" +
                    "}, {\n" +
                    "\t\"px\": 3.0300,\n" +
                    "\t\"qty\": 40000000.0000\n" +
                    "}, {\n" +
                    "\t\"px\": 3.0325,\n" +
                    "\t\"qty\": 60000000.0000\n" +
                    "}, {\n" +
                    "\t\"px\": 3.0500,\n" +
                    "\t\"qty\": 20000000.0000\n" +
                    "}]\n" +
                    "}");

        }


        @Test
        public void quoteCommondTest(){

            IRSQuoteCommand irsQuoteCommand = new IRSQuoteCommand("FR007_2Y","2019-04-01 16:52:18.631",2,false);
            msgType = new MsgType("MDCI", JSON.toJSONString(irsQuoteCommand).toString());
        }

        @After
        public void after(){

            jsonQueue.add(msgType);
            new TransDumper(config, jsonQueue).start();
            NettyClient client = new NettyClient(config, jsonQueue);
            client.connect();
        }
    }
