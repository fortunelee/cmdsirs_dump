package com.orient.constants;

public class SqlConstants {
	
	//public final static String DbSqlTemplate = "if not exists (select * from sysdatabases where name='XSWAP_%s') create database XSWAP_%s;";
	
	public final static String DbSqlTemplate = "if not exists (select * from sysdatabases where name='XSWAP_%s') create database XSWAP_%s CONTAINMENT=NONE\r\n" + 
			"ON PRIMARY\r\n" + 
			"(\r\n" + 
			"	NAME=\"XSWAP_%s\",FILENAME=\"%s\\XSWAP_%s.mdf\",SIZE=5120KB,MAXSIZE=UNLIMITED,FILEGROWTH=1024KB\r\n" + 
			")\r\n" + 
			"LOG ON\r\n" + 
			"(\r\n" + 
			"	NAME=\"XSWAP_%s_log\",FILENAME=\"%s\\XSWAP_%s_log.ldf\",SIZE=3456KB,MAXSIZE=UNLIMITED,FILEGROWTH=10%%\r\n" + 
			");";
	
	public final static String CreateTransTableSqlTemplate = "use XSWAP_%s; if not exists (select * from sys.tables where name='CMDSTrans_%s')\r\n" +
							" create table CMDSTrans_%s (\r\n" +
							" id int identity(1,1) primary key,\r\n"  +
							" securityID varchar(30) not null,\r\n" +
							" senderCompID varchar(30) null,\r\n" +
							" targetCompID varchar(30) null,\r\n" +
							" transactTime varchar(30) null,\r\n" + 
							" lastestRate varchar(30) null,\r\n" +
							" tradeVolume varchar(30) null,\r\n" +
							" openingRate varchar(30) null,\r\n" +
							" highestRate varchar(30) null,\r\n" +
							" lowestRate varchar(30) null,\r\n" +
							" intradayPrice varchar(30) null,\r\n" +
							" totalVolume varchar(30) null)";

	public final static String InsertSqlTemplate = "use XSWAP_%s;insert into CMDSTrans_%s values(?,?,?,?,?,?,?,?,?,?,?)";


	public final static String CreateBestTableSqlTemplate = "use XSWAP_%s; if not exists (select * from sys.tables where name='CMDSBest_%s')\r\n" +
							" create table CMDSBest_%s (\r\n" +
							" id int identity(1,1) primary key,\r\n"  +
							" securityID varchar(30) not null,\r\n" +
							" senderCompID varchar(30) null,\r\n" +
							" levels varchar(30) null,\r\n" +
							" targetCompID varchar(30) null,\r\n" +
							" transactTime varchar(30) null,\r\n" +
							" bidPrice1 varchar(30) null,\r\n" +
							" bidVolume1 varchar(30) null,\r\n" +
							" askPrice1 varchar(30) null,\r\n" +
							" askVolume1 varchar(30) null,\r\n" +
							" bidClear varchar(30) null,\r\n" +
							" askClear varchar(30) null)";

	public final static String InsertBestSqlTemplate = "use XSWAP_%s;insert into CMDSBest_%s values(?,?,?,?,?,?,?,?,?,?,?)";

	public final static String CreateDepthSingleTableSqlTemplate = "use XSWAP_%s; if not exists (select * from sys.tables where name='CMDSDepthSingle_%s')\r\n" +
			" create table CMDSDepthSingle_%s (\r\n" +
			" id int identity(1,1) primary key,\r\n"  +
			" securityID varchar(30) not null,\r\n" +
			" senderCompID varchar(30) null,\r\n" +
			" levels varchar(30) null,\r\n" +
			" targetCompID varchar(30) null,\r\n" +
			" transactTime varchar(30) null,\r\n" +
			" bidPrice1 varchar(30) null,\r\n" +
			" bidVolume1 varchar(30) null,\r\n" +
			" bidPrice2 varchar(30) null,\r\n" +
			" bidVolume2 varchar(30) null,\r\n" +
			" bidPrice3 varchar(30) null,\r\n" +
			" bidVolume3 varchar(30) null,\r\n" +
			" bidPrice4 varchar(30) null,\r\n" +
			" bidVolume4 varchar(30) null,\r\n" +
			" bidPrice5 varchar(30) null,\r\n" +
			" bidVolume5 varchar(30) null,\r\n" +
			" askPrice1 varchar(30) null,\r\n" +
			" askVolume1 varchar(30) null,\r\n" +
			" askPrice2 varchar(30) null,\r\n" +
			" askVolume2 varchar(30) null,\r\n" +
			" askPrice3 varchar(30) null,\r\n" +
			" askVolume3 varchar(30) null,\r\n" +
			" askPrice4 varchar(30) null,\r\n" +
			" askVolume4 varchar(30) null,\r\n" +
			" askPrice5 varchar(30) null,\r\n" +
			" askVolume5 varchar(30) null,\r\n" +
			" bidClear varchar(30) null,\r\n" +
			" askClear varchar(30) null)";

	public final static String InsertDepthSingleqlTemplate = "use XSWAP_%s;insert into CMDSDepthSingle_%s values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


	public final static String CreateDepthDoubleTableSqlTemplate = "use XSWAP_%s; if not exists (select * from sys.tables where name='CMDSDepthDouble_%s')\r\n" +
			" create table CMDSDepthDouble_%s (\r\n" +
			" id int identity(1,1) primary key,\r\n"  +
			" securityID varchar(30) not null,\r\n" +
			" symbol varchar(30) null,\r\n" +
			" time varchar(30) null,\r\n" +
			" bidPrice1 varchar(30) null,\r\n" +
			" bidVolume1 varchar(30) null,\r\n" +
			" bidPrice2 varchar(30) null,\r\n" +
			" bidVolume2 varchar(30) null,\r\n" +
			" bidPrice3 varchar(30) null,\r\n" +
			" bidVolume3 varchar(30) null,\r\n" +
			" bidPrice4 varchar(30) null,\r\n" +
			" bidVolume4 varchar(30) null,\r\n" +
			" bidPrice5 varchar(30) null,\r\n" +
			" bidVolume5 varchar(30) null,\r\n" +
			" askPrice1 varchar(30) null,\r\n" +
			" askVolume1 varchar(30) null,\r\n" +
			" askPrice2 varchar(30) null,\r\n" +
			" askVolume2 varchar(30) null,\r\n" +
			" askPrice3 varchar(30) null,\r\n" +
			" askVolume3 varchar(30) null,\r\n" +
			" askPrice4 varchar(30) null,\r\n" +
			" askVolume4 varchar(30) null,\r\n" +
			" askPrice5 varchar(30) null,\r\n" +
			" askVolume5 varchar(30) null)";

	public final static String InsertDepthDoublesqlTemplate = "use XSWAP_%s;insert into CMDSDepthDouble_%s values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

}
