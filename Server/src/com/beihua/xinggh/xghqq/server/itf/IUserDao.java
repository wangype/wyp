package com.beihua.xinggh.xghqq.server.itf;

import java.util.ArrayList;

import com.beihua.xinggh.xghqq.server.vo.User;

/**
 * @category 用户操作接口
 * @author xinggh
 *
 */
public interface IUserDao {

	/**
	 * @category 用户注册
	 * @param u
	 * @return
	 */
	public abstract int register(User u);

	/**
	 * @category 用户登录
	 * @param u
	 * @return
	 */
	public ArrayList<User> login(User u);

	/**
	 * @category 刷新好友
	 * @param id
	 * @return
	 */
	public ArrayList<User> refresh(int id);
	
	/**
	 * @category 退出
	 * @param id
	 */
	public void logout(int id);
}
