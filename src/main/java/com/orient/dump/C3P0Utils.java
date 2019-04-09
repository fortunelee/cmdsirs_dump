package com.orient.dump;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.orient.common.ReadConfig;

public class C3P0Utils {
	
	private final static Logger logger = LoggerFactory.getLogger(C3P0Utils.class);
	
	//1.注册驱动
	private ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	public DataSource getDataSource()
	{
		return dataSource;
	}
	
	// serverAddr: [ip:port], 没有[]
	public C3P0Utils(String user, String pwd, String serverAddr)
	{
		this.user = user;
		this.pwd = pwd;
		this.sqlAddr = user;
		this.dumpOn = true;
		
		if (initC3P0Util(user, pwd, serverAddr))
			logger.info("C3P0 init ok.");
		else {
			logger.error("C3P0 init failed.");
			System.exit(2);
		}
	}
	
	private String user = null;
	private String pwd = null;
	private String sqlAddr = null;
	private String path = "C:";   // 默认存C盘
	private boolean dumpOn = false;
	
	private ReadConfig config = null;
	
	private CreateTableUtil createTableUtil = null;   // new完可以执行完初始化生成Database,Table
	
	public C3P0Utils(ReadConfig config) throws Exception
	{
		this.config = config;
		if (!init()) {
			logger.error("C3P0 init config failed.");
			System.exit(1);
		}
		
		if (dumpOn) {
			createTableUtil = new CreateTableUtil("jdbc:sqlser" +
					"ver://" + sqlAddr, user, pwd, path);
			
			if (!initC3P0Util(user, pwd, sqlAddr)) {
				logger.error("C3P0 init failed.");
				System.exit(2);
			}
		}
		else
			logger.info("DumpOn is false.");
	}
	
	private boolean init()
	{
		String dumpOn = config.getConfigItem("DumpON");
		if (!(dumpOn == null || dumpOn.isEmpty()))
			if (dumpOn.equalsIgnoreCase("true"))
				this.dumpOn = true;
			else if (dumpOn.equalsIgnoreCase("false"))
				this.dumpOn = false;
		
		String sqlAddr = config.getConfigItem("SQLAddr");
		if (!(sqlAddr == null || sqlAddr.isEmpty()))
			this.sqlAddr = sqlAddr;
		
		String user = config.getConfigItem("SQLUser");
		if (!(user == null || user.isEmpty()))
			this.user = user;
		
		String pwd = config.getConfigItem("SQLPwd");
		if (!(pwd == null || pwd.isEmpty()))
			this.pwd = pwd;
		
		String path = config.getConfigItem("SQLPath");
		if (!(path == null || path.isEmpty()))
			this.path = path;
		
		return true;
	}
	
	private boolean initC3P0Util(String user, String pwd, String serverAddr)
	{
		try {
			String year = CreateTableUtil.getYear();
			
			dataSource.setDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			dataSource.setJdbcUrl("jdbc:sqlserver://" + serverAddr + ";databaseName=XSWAP_" + year);
			dataSource.setUser(user);
			dataSource.setPassword(pwd);
			dataSource.setMinPoolSize(5);
			dataSource.setMaxPoolSize(10);
			dataSource.setInitialPoolSize(5);
			dataSource.setAcquireIncrement(5);
			
			return true;
		} catch (PropertyVetoException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Connection getConection() {
		if (!dumpOn)
			return null;
		
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public void close(ResultSet rs, Statement state, Connection conn) {
		if (!dumpOn)
			return;
		
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (state != null) {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
