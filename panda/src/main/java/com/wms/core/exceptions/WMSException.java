package com.wms.core.exceptions;

import org.apache.commons.logging.LogFactory;

/**
 * 系统自定义异常
 * 当系统发生逻辑错误时，可抛出此异常
 * <p>
 * xb
 */
@SuppressWarnings("serial")
public class WMSException extends RuntimeException {
    public WMSException(String message) {
        super(message);
        printStackTrace();
        var log = LogFactory.getLog(this.getClass());
        log.error(message);
    }

    public WMSException(String message, Throwable throwable) {
        super(message, throwable);
        printStackTrace();
        var log = LogFactory.getLog(this.getClass());
        log.error(message, throwable);
    }
    public WMSException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private String errCode;
    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
