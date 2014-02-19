package com.beihua.xinggh.xghqq.server.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.beihua.xinggh.xghqq.server.itf.IUserDao;
import com.beihua.xinggh.xghqq.server.util.DBUtil;
import com.beihua.xinggh.xghqq.server.util.MD5CryptUtil;
import com.beihua.xinggh.xghqq.server.util.MyDate;
import com.beihua.xinggh.xghqq.server.vo.User;

public class UserDaoImpl implements IUserDao {

	/**
	 * 用户注册,注册成功以后需要设置默认的 yq邮箱，而且创建一个存储该用户好友的表
	 */
	@Override
	public int register(User u) {
		DBUtil dbutil = DBUtil.getDBUtil();
		int id;
		Connection con = dbutil.getConnection();
		String sql1 = "insert into yquser(name,password,time,sex,age,lev) values(?,?,?,?,?,?)";
		String sql2 = "select uid from yquser";
		String sql3 = "update yquser set email = ? where uid = ?";
		String md5pd = MD5CryptUtil.getMd5CryptUtil().convertToMD5(u.getPassword()); 
		try {
			PreparedStatement ps = con.prepareStatement(sql1);
			ps.setString(1, u.getName());
			ps.setString(2, md5pd);
			ps.setString(3, MyDate.getDateCN());
			ps.setString(4, u.getSex());
			ps.setInt(5, u.getAge());
			ps.setInt(6, u.getLev());
			int res = ps.executeUpdate();
			if (res > 0) {
				PreparedStatement ps2 = con.prepareStatement(sql2);
				ResultSet rs = ps2.executeQuery();
				if (rs.last()) {
					id = rs.getInt("uid");
					PreparedStatement ps3 = con.prepareStatement(sql3);
					ps3.setString(1, id +"@yq.com");
					ps3.setInt(2, id);
					ps3.executeUpdate();
					// 注册成功后，创建一个已用户id为表名的表，用于存放好友信息
					createFriendtable(id);
					return id;
				}
			}
		} catch (SQLException e) {
			 e.printStackTrace();
		} finally {
			dbutil.closeConnection(con);
		}
		return 0;
	}

	/**
	 * 用户登录，返回用户好友列表
	 */
	@Override
	public ArrayList<User> login(User u) {
		DBUtil dbutil = DBUtil.getDBUtil();
		Connection con = dbutil.getConnection();
		String sql = "select * from yquser where uid=? and password=?";
		String md5pd = MD5CryptUtil.getMd5CryptUtil().convertToMD5(u.getPassword());
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, u.getUid());
			ps.setString(2, md5pd);
			ResultSet rs = ps.executeQuery();
			if (rs.first()) {
				// 更新表状态为在线
				setOnline(u.getUid());
				//刷新好友列表
				ArrayList<User> refreshList = refresh(u.getUid());
				return refreshList;
			}
		} catch (SQLException e) {
			 e.printStackTrace();
		} finally {
			dbutil.closeConnection(con);
		}
		return null;
	}

	/**
	 * 刷新好友列表,从以用户UID创建的表中获取所有用户的信息
	 */
	@Override
	public ArrayList<User> refresh(int id) {
		DBUtil dbutil = DBUtil.getDBUtil();
		ArrayList<User> list = new ArrayList<User>();
		User me = findMe(id);
		list.add(me);// 先添加自己
		Connection con = dbutil.getConnection();
		String sql = "select * from ? ";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.first()) {
				do {
					User friend = new User();
					friend.setUid(rs.getInt("yq"));
					friend.setName(rs.getString("name"));
					friend.setIsonline(rs.getInt("isonline"));
					friend.setImg(rs.getString("img"));
					friend.setGroup(rs.getString("group"));
					friend.setEmail(rs.getString("email"));
					friend.setAge(rs.getInt("age"));
					friend.setSex(rs.getString("sex"));
					friend.setLev(rs.getInt("lev"));
					list.add(friend);
				} while (rs.next());
			}
			return list;
		} catch (SQLException e) {
			// e.printStackTrace();
		} finally {
			dbutil.closeConnection(con);
		}
		return null;
	}

	/**
	 * 查找自己
	 */
	public User findMe(int id) {
		DBUtil dbutil = DBUtil.getDBUtil();
		User me = new User();
		Connection con = dbutil.getConnection();
		String sql = "select * from qyuser where uid=?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.first()) {
				me.setUid(rs.getInt("uid"));
				me.setEmail(rs.getString("email"));
				me.setName(rs.getString("name"));
				me.setImg(rs.getString("img"));
				me.setAge(rs.getInt("age"));
				me.setSex(rs.getString("sex"));
				me.setLev(rs.getInt("lev"));
				me.setGroup(rs.getString("group"));
				me.setIsonline(rs.getInt("isonline"));
			}
			return me;
		} catch (SQLException e) {
			 e.printStackTrace();
		} finally {
			dbutil.closeConnection(con);
		}
		return null;
	}
	/**
	 * 下线，退出
	 */
	@Override
	public void logout(int id) {
		DBUtil dbutil = DBUtil.getDBUtil();
		Connection con = dbutil.getConnection();
		try {
			String sql = "update yquser set isonline=0 where id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			updateAllOff(id);//同步更新好友的自己的状态为离线状态
			// System.out.println(res);
		} catch (SQLException e) {
			 e.printStackTrace();
		} finally {
			dbutil.closeConnection(con);
		}
	}
	/**
	 * 更新好友的自己的状态为离线状态
	 * 
	 * @param id
	 */
	public void updateAllOff(int id) {
		DBUtil dbutil = DBUtil.getDBUtil();
		Connection con = dbutil.getConnection();
		try {
			String sql = "update ? set isonline=0 where yq=?";
			PreparedStatement ps = con.prepareStatement(sql);
			for (int offId : getAllId()) {
				ps.setInt(1, offId);
				ps.setInt(2, id);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			 e.printStackTrace();
		} finally {
			dbutil.closeConnection(con);
		}
	}
	/**
	 * 设置状态为在线
	 * 
	 * @param id
	 */
	public void setOnline(int id) {
		DBUtil dbutil = DBUtil.getDBUtil();
		Connection con = dbutil.getConnection();
		try {
			String sql = "update yquser set isonline=1 where id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			// 更新所有表状态为在线
			updateAllOn(id);
		} catch (SQLException e) {
			 e.printStackTrace();
		} finally {
			dbutil.closeConnection(con);
		}
	}
	/**
	 * 在qq为id的用户所有好友中，更新该用户为上线
	 * 
	 * @param id
	 */
	public void updateAllOn(int id) {
		DBUtil dbutil = DBUtil.getDBUtil();
		Connection con = dbutil.getConnection();
		try {
			String sql = "update ? set isonline=1 where yq=?";
			PreparedStatement ps = con.prepareStatement(sql);
			for (int OnId : getAllId()) {
				ps.setInt(1, OnId);
				ps.setInt(2, id);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			 e.printStackTrace();
		} finally {
			dbutil.closeConnection(con);
		}
	}
	public List<Integer> getAllId() {
		DBUtil dbutil = DBUtil.getDBUtil();
		Connection con = dbutil.getConnection();
		List<Integer> list = new ArrayList<Integer>();
		try {
			String sql = "select uid from yquser";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.first()) {
				do {
					int id = rs.getInt("uid");
					list.add(id);
				} while (rs.next());
			}
			// System.out.println(list);
			return list;
		} catch (SQLException e) {
			 e.printStackTrace();
		} finally {
			dbutil.closeConnection(con);
		}
		return null;
	}
	/**
	 * 注册成功后，创建一个用户表，保存该用户好友
	 * 
	 * @param id
	 */
	public void createFriendtable(int id) {
		DBUtil dbutil = DBUtil.getDBUtil();
		Connection con = dbutil.getConnection();
		try {
			String sql = "create table " + id
					+ " (id int auto_increment not null primary key,"
					+ "name varchar(20) not null,"
					+ "isonline int(11) not null default 0,"
					+ "group int(11) not null default 0,"
					+ "yq int(16) not null default 0,"
					+ "img int(11) not null default 0)";
			PreparedStatement ps = con.prepareStatement(sql);
			int res = ps.executeUpdate();
			System.out.println(res);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbutil.closeConnection(con);
		}
	}

}
