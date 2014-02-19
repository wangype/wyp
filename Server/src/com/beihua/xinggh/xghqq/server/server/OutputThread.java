package com.beihua.xinggh.xghqq.server.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OutputThread extends Thread {

	private ObjectOutputStream oos;
	private boolean isStart = true;// 循环标志位
	private Socket socket;
	private SocketObject tranObj;

	public OutputThread(Socket socket) {
		this.socket = socket;
	}

	public void sendMessage(SocketObject tranObj) {
		this.tranObj = tranObj;
		synchronized (this) {
			notify();
		}
	}

	@Override
	public void run() {
		while (isStart) {
			if (tranObj != null) {
				try {
					synchronized (this) {
						wait();
					}
					oos.writeObject(socket.getOutputStream());
					oos.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
			// 关闭资源
			try {
				if (oos != null)
					oos.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
