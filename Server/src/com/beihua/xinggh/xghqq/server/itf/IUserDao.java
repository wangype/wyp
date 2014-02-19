package com.beihua.xinggh.xghqq.server.itf;

import java.util.ArrayList;

import com.beihua.xinggh.xghqq.server.vo.User;

/**
 * @category �û������ӿ�
 * @author xinggh
 *
 */
public interface IUserDao {

	/**
	 * @category �û�ע��
	 * @param u
	 * @return
	 */
	public abstract int register(User u);

	/**
	 * @category �û���¼
	 * @param u
	 * @return
	 */
	public ArrayList<User> login(User u);

	/**
	 * @category ˢ�º���
	 * @param id
	 * @return
	 */
	public ArrayList<User> refresh(int id);
	
	/**
	 * @category �˳�
	 * @param id
	 */
	public void logout(int id);
}
