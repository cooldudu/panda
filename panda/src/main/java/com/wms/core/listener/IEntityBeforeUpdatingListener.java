package com.wms.core.listener;

import org.aspectj.lang.ProceedingJoinPoint;

import com.wms.core.exceptions.WMSException;

/**
 * 更新实体前监听器接口
 *
 * @author xb
 *
 */
public interface IEntityBeforeUpdatingListener {
	/**
	 * 执行处理
	 *
	 * @param entity
	 */
	boolean processPrepareUpdate(Object entity,
                                 ProceedingJoinPoint proceedingJoinPoint) throws WMSException;
}
