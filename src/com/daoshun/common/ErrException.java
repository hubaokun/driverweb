package com.daoshun.common;

import com.daoshun.exception.NullParameterException;

public class ErrException extends NullParameterException {
	/**
	 * 错误提示信息 
	 */
	private String msg;
	
	public ErrException(String msg) {
		super();
		this.msg = msg;
	}
	
	public ErrException() {
		super();
	}

	private static final long serialVersionUID = 1L;
	public String getMessage() {
		if(msg!=null && !"".equals(msg)){
			return "{\"message\":\""+msg+"\",\"code\":98}";
		}else{
			return "{\"message\":\"请升级到最新版本\",\"code\":99}";
		}
	}
}
