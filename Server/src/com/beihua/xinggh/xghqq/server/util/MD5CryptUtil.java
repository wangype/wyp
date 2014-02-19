package com.beihua.xinggh.xghqq.server.util;

import java.security.MessageDigest;
  
/** 
 * 采用MD5加密解密 
 * @author tfq 
 * @datetime 2011-10-13 
 */  
public class MD5CryptUtil {  
	
	private static MD5CryptUtil md5CryptUtil = null;

	private MD5CryptUtil(){}
    public static MD5CryptUtil getMd5CryptUtil() {
    	if(md5CryptUtil == null){
    		md5CryptUtil = new MD5CryptUtil();
    	}
		return md5CryptUtil;
	}

	/*** 
     * MD5加码 生成32位md5码 
     */  
    public String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    }  
  
    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    private String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
    }
    /**
     * 给字符串加密
     * @param str
     * @return
     */
    public String convertToMD5(String str){
    	return convertMD5(string2MD5(str));
    }
    /**
     * 给字符串解密
     * @param str
     * @return
     */
    public String convertGetMD5(String str){
    	return convertMD5(convertMD5(string2MD5(str)));
    }
    // 测试主函数  
    public static void main(String args[]) {  
        String s = new String("xinggh1231shaahjdasjhassdsfdsdfdsaasddsdsdsdsdsadsaas");  
        System.out.println("原始：" + s);  
        System.out.println("MD5后：" + getMd5CryptUtil().string2MD5(s));
        System.out.println("MD5后：" + getMd5CryptUtil().string2MD5(s));  
        System.out.println("加密的：" + getMd5CryptUtil().convertToMD5(s));  
        System.out.println("解密的：" + getMd5CryptUtil().convertGetMD5(s));  
    }  
}  
