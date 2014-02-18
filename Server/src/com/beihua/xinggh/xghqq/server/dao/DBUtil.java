package com.beihua.xinggh.xghqq.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBUtil {
	private static DBUtil dbutil;
	private DBUtil(){
		
	}
	public synchronized static DBUtil getDBUtil(){
		if(dbutil==null){
			dbutil=new DBUtil();
		}
		
		return dbutil;
	}
	
	public Connection getConnection(){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;databaseName=yq",
					"sa","123");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public void closeConnection(Connection con){
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}