package com.wms.core.listener;

import org.aspectj.lang.ProceedingJoinPoint;

import com.wms.core.exceptions.WMSException;

/**
 * 更新实体后监听器接口
 *
 * @author xb
 *
 */
public interface IEntityAfterUpdatingListener {
	/**
	 * 执行处理
	 *
	 * @param entity
	 */
	boolean processUpdated(Object entity,
                           ProceedingJoinPoint proceedingJoinPoint) throws WMSException;
}
