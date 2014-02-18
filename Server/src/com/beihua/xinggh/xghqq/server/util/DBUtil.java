package com.beihua.xinggh.xghqq.server.util;

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
		Connection con = null;
		String url = "jdbc:mysql://10.11.80.59:3306/yq";
		String username = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
	           System.out.println("数据库连接失败");
		}
		return con;
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
