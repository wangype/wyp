package com.beihua.xinggh.xghqq.server.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import com.beihua.xinggh.xghqq.server.impl.UserDaoFactory;
import com.beihua.xinggh.xghqq.server.itf.IUserDao;
import com.beihua.xinggh.xghqq.server.vo.User;


public class InputThread extends Thread{
	private Socket socket;// socket����
	private OutputThread out;// ���ݽ�����д��Ϣ�߳�
	private ObjectInputStream objinputStream;// ����������
	private boolean isStart = true;// �Ƿ�ѭ������Ϣ
    private SocketObject socketObject;
	private ThreadMap map;
	
	public InputThread(Socket socket, OutputThread out, ThreadMap map){
		this.socket = socket;
		this.out = out;
		this.map = map;
		try {
			objinputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(isStart){
			try {
				handleMessage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			try {
				if (objinputStream != null)
					objinputStream.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
  	
	
	//���ܿͻ��˷��͵���Ϣ���з��ദ��
	private void handleMessage() throws IOException, ClassNotFoundException, BusinessExection{
		Object readObj = objinputStream.readObject();
		if(readObj != null){
			socketObject = (SocketObject)readObj;
			int key = socketObject.getSendType();
			int id = socketObject.getTo_user();
			User user = socketObject.getUser();    //��ȡ�û���Ϣ����ע��
			if(user == null)
				throw new BusinessExection("�û�Ϊ�գ�");
			IUserDao userDao = UserDaoFactory.getInstance();
			if(key == Type.LOGIN){
				userDao.login(user);
				map.add(id, out);
			}else if(key == Type.REGISTER){
				userDao.register(user);
			}else if(key == Type.SENDSINGLE){
				OutputThread outputThread = map.getById(id);
				if(outputThread != null)    //��ʾ�û�����
					outputThread.sendMessage(socketObject);
			}else if(key == Type.SENDALL){
				List<OutputThread> alluser = map.getAll();
				for (int i = 0; i < alluser.size(); i++) {
					alluser.get(i).sendMessage(socketObject);
				}
			}else if(key == Type.LOGINOUT){
				map.remove(id);
			}
		}
	}
	
	
	
	
}
