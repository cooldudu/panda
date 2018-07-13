package com.wms.core.listener;

import org.aspectj.lang.ProceedingJoinPoint;

import com.wms.core.exceptions.WMSException;

/**
 * 删除实体前监听器接口
 *
 * @author xb
 *
 */
public interface IEntityBeforeDeletingListener {
	/**
	 * 执行处理
	 *
	 * @param entity
	 */
	boolean processPrepareDelete(Object entity,
                                 ProceedingJoinPoint proceedingJoinPoint) throws WMSException;
}
