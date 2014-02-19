package com.beihua.xinggh.xghqq.server.impl;

import com.beihua.xinggh.xghqq.server.itf.IUserDao;

public class UserDaoFactory {
	private static IUserDao dao;
	public static IUserDao getInstance() {
		if (dao == null) {
			dao = new UserDaoImpl();
		}
		return dao;
	}
}
