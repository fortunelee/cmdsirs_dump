package com.orient.dump;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orient.constants.SqlConstants;

public class CreateTableUtil {
	private final static Logger logger = LoggerFactory.getLogger(CreateTableUtil.class);
	
	public static String getDate()
	{
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(today);
	}
	
	public static String getYear()
	{
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(today);
	}
	
	
	public CreateTableUtil(String url, String user, String pwd, String path) throws Exception
	{
		if (!InitDB(url, user, pwd, path))
		{
			logger.error("create table failed.");
			throw new Exception("CreateTableUtil failed.");
			//System.exit(2);
		}
	}
	
	private boolean InitDB(String url, String user, String pwd, String path)
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			logger.error("class com.microsoft.sqlserver.jdbc.SQLServerDriver not found.");
			return false;
		}
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = DriverManager.getConnection(url, user, pwd);
			stmt = conn.createStatement();

			String today = getDate();
			String year = getYear();
			String createDatabaseSqlTemplate = SqlConstants.DbSqlTemplate;

			String createTransTableSqlTemplate = SqlConstants.CreateTransTableSqlTemplate;
			String CreateBestTableSqlTemplate = SqlConstants.CreateBestTableSqlTemplate;
            String CreateDepthSingleTableSqlTemplate = SqlConstants.CreateDepthSingleTableSqlTemplate;
			String CreateDepthDoubleTableSqlTemplate = SqlConstants.CreateDepthDoubleTableSqlTemplate;

			String createDBSql = String.format(createDatabaseSqlTemplate, year, year, year, path, year, year, path, year);
			String createDepthSql = String.format(createTransTableSqlTemplate, year, today, today);
			String CreateBestSql = String.format(CreateBestTableSqlTemplate, year, today, today);
            String CreateDepthSingleSql = String.format(CreateDepthSingleTableSqlTemplate, year, today, today);
			String CreateDepthDoubleSql = String.format(CreateDepthDoubleTableSqlTemplate, year, today, today);


            int createDBSqlReturn = stmt.executeUpdate(createDBSql);
			if (createDBSqlReturn == 0)
				logger.info("DBSql:{} ok.", createDBSql);
			else if (createDBSqlReturn == -1)
				logger.info("DBSql:{} already exists.", createDBSql);
			
			int createDepthSqlReturn = stmt.executeUpdate(createDepthSql);
			if (createDepthSqlReturn == 0)
				logger.info("DepthSql:{} ok.", createDepthSql);
			else
				logger.info("DepthSql:{} already exists.", createDepthSql);

			int createBestSqlReturn = stmt.executeUpdate(CreateBestSql);
			if (createBestSqlReturn == 0)
				logger.info("BestSql:{} ok.", CreateBestSql);
			else
				logger.info("BestSql:{} already exists.", CreateBestSql);

            int createDepthSingleSqlReturn = stmt.executeUpdate(CreateDepthSingleSql);
            if (createDepthSingleSqlReturn == 0)
                logger.info("DepthSingleSql:{} ok.", CreateDepthSingleSql);
            else
                logger.info("DepthSingleSql:{} already exists.", CreateDepthSingleSql);

			int createDepthDoubleSqlReturn = stmt.executeUpdate(CreateDepthDoubleSql);
			if (createDepthDoubleSqlReturn == 0)
				logger.info("DepthDoubleSql:{} ok.", CreateDepthDoubleSql);
			else
				logger.info("DepthDoubleSql:{} already exists.", CreateDepthDoubleSql);

			return true;
		} catch(Exception e) {
			logger.error("exception: " + e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				return false;
			}
		}
		
		return true;
	}
	
//	public static void main(String[] args) {
//		String url = "jdbc:sqlserver://localhost:1433";
//		String user = "chengang";
//		String password = "123456";
//		
//		InitDB(url, user, password);
//	}
	
}
