package com.wms.core.listener;

import org.aspectj.lang.ProceedingJoinPoint;

import com.wms.core.exceptions.WMSException;

/**
 * 删除实体后监听器接口
 *
 * @author xb
 *
 */
public interface IEntityAfterDeletingListener {

	/**
	 * 执行处理
	 *
	 * @param entity
	 */
	boolean processDeleted(Object entity,
                           ProceedingJoinPoint proceedingJoinPoint) throws WMSException;
}
