package com.beihua.xinggh.xghqq.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	private static Connection connection = null;

	private DBConnection() {
		String url = "jdbc:mysql://10.11.80.59:3306/yq";
		String username = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println("数据库连接失败");
		}
	}

	public static Connection getConnection() {
		if(connection == null){
			new DBConnection();
		}
		return connection;
	}
}
