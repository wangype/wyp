package com.beihua.xinggh.xghqq.server.server;

import com.beihua.xinggh.xghqq.server.vo.User;

public class SocketObject {

	private int sendType;

	private int from_user;
	private int to_user;
	private User user;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getFrom_user() {
		return from_user;
	}

	public void setFrom_user(int from_user) {
		this.from_user = from_user;
	}

	public int getTo_user() {
		return to_user;
	}

	public void setTo_user(int to_user) {
		this.to_user = to_user;
	}

	public int getSendType() {
		return sendType;
	}

	public void setSendType(int sendType) {
		this.sendType = sendType;
	}

	
}
