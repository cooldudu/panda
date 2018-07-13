package com.wms.core.exceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 系统自定义异常
 * 当系统发生逻辑错误时，可抛出此异常
 * 
 * xb
 */
@SuppressWarnings("serial")
public class WMSException extends Exception
{
	public WMSException(String message)
	{
		super(message);
		printStackTrace();
		Log log = LogFactory.getLog(this.getClass());
		log.error(message);
	}

	public WMSException(String message, Throwable throwable)
	{
		super(message, throwable);
		printStackTrace();
		Log log = LogFactory.getLog(this.getClass());
		log.error(message,throwable);
	}
}
