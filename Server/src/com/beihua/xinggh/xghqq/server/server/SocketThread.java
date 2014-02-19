package com.beihua.xinggh.xghqq.server.server;

import java.net.Socket;

/**
 * 管理输出线程和输入线程
 * @author Administrator
 *
 */
public class SocketThread implements Runnable{

	private Socket socket = null;
	private InputThread in;
	private OutputThread out;
	private ThreadMap map;
	
	public SocketThread(Socket socket){
		this.socket = socket;
		map = ThreadMap.getInstance();
	}
	
	@Override
	public void run() {
		System.out.println("创建会话连接线程");
		out = new OutputThread(socket);
		in = new InputThread(socket, out, map);
		in.start();
		out.start();
	}

}
