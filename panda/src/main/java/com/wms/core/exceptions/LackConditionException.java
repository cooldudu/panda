package com.wms.core.exceptions;

/**
 * 系统自定义异常
 * 当系统发生逻辑错误时，可抛出此异常
 * 
 * xb
 */
@SuppressWarnings("serial")
public class LackConditionException extends Exception
{
	public LackConditionException(String code)
	{
		super(code);
		printStackTrace();
	}

	public LackConditionException(String code, Throwable throwable)
	{
		super(code, throwable);
		printStackTrace();
	}
}
