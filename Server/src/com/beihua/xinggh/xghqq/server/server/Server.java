package com.beihua.xinggh.xghqq.server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private static int PORT = 8080;

	private ExecutorService executorService; // 线程池
	private ServerSocket serverSocket = null;
	private Socket socket = null;

	private boolean isStarted = true;

	public Server() {
		try {
			if (executorService == null) {
				executorService = Executors.newCachedThreadPool();
				serverSocket = new ServerSocket(PORT);
			}
		} catch (IOException e) {
			e.printStackTrace();
			isStarted = false;
		}
	}

	public void startServer() {
		System.out.println("服务器已经启动。。。");
		while (isStarted) {
			try {
				socket = serverSocket.accept();
				if (socket.isConnected())
					executorService.execute(new SocketThread(socket));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (socket != null)
			try {
				socket.close();
				if (serverSocket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				executorService.shutdown();
			}
	}

}
