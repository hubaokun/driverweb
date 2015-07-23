package com.daoshun.common;

import com.daoshun.exception.NullParameterException;

public class ErrException extends NullParameterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "{\"message\":\"请生最新版本\",\"code\":99}";
	}
}
