package test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectTest {

	private static Connection connection = null;
	
	public static void main(String[] args) {
		
		Connection conn;
		conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from yq1";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("code"));
			}
			closeConn();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static Connection getConnection(){
		String url = "jdbc:mysql://10.11.80.59:3306/yq";
		String username = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
	           System.out.println("数据库连接失败");
		}
		return connection;
	}
	
	private static void closeConn(){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
