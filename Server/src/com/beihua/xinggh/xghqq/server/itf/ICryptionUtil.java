package com.beihua.xinggh.xghqq.server.itf;


public interface ICryptionUtil {

	/**
	 * ����MD5����
	 * 
	 * @param info
	 *            Ҫ���ܵ���Ϣ
	 * @return String ���ܺ���ַ���
	 */
	public String encryptToMD5(String info) ;
	/**
	 * �����ܳ׽���DES����
	 * 
	 * @param key
	 *            �ܳ�
	 * @param sInfo
	 *            Ҫ���ܵ�����
	 * @return String ���ؽ��ܺ���Ϣ
	 */
	public String decryptByDES(String sInfo) ;
}
