package com.beihua.xinggh.xghqq.server.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �洢д�̵߳Ļ�����
 * @author Administrator
 *
 */
public class ThreadMap {

	private  HashMap<Integer, OutputThread> map;
	private static ThreadMap instance;

	//����ģʽ
	private ThreadMap(){
		map = new HashMap<Integer, OutputThread>();
	}
	
	public synchronized static ThreadMap getInstance(){
		if(instance == null){
			instance = new ThreadMap();
		}
		return instance;
	}
	
	// ���д�̵߳ķ���
		public synchronized void add(Integer id, OutputThread out) {
			map.put(id, out);
		}

		// �Ƴ�д�̵߳ķ���
		public synchronized void remove(Integer id) {
			map.remove(id);
		}

		// ȡ��д�̵߳ķ���,Ⱥ�ĵĻ������Ա���ȡ����Ӧд�߳�
		public synchronized OutputThread getById(Integer id) {
			return map.get(id);
		}

		// �õ�����д�̷߳��������������������û����͹㲥
		public synchronized List<OutputThread> getAll() {
			List<OutputThread> list = new ArrayList<OutputThread>();
			for (Map.Entry<Integer, OutputThread> entry : map.entrySet()) {
				list.add(entry.getValue());
			}
			return list;
		}
}
