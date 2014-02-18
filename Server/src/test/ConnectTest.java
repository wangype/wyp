package test;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.beihua.xinggh.xghqq.server.util.DBUtil;

public class ConnectTest {
	
	public static void main(String[] args) {
		DBUtil dbutil = DBUtil.getDBUtil();
		Connection conn;
		conn = dbutil.getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from yq1";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("code"));
				System.out.println(rs.getString("name"));
			}
			dbutil.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
