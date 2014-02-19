package com.beihua.xinggh.xghqq.server.server;

/**
 * 自定义异常类
 * @author Administrator
 *
 */
public class BusinessExection extends Exception{

	    public BusinessExection(String s)
	    {
	        super(s);
	        errorCodeString = s;
	        setErrorCodeString("-32000");
	    }


	    public String getErrorCodeString()
	    {
	        return errorCodeString;
	    }

	    public void setErrorCodeString(String errorCode)
	    {
	        errorCodeString = errorCode;
	    }

	    static final long serialVersionUID = -35466L;
	    private String errorCodeString;

}
