package com.wms.core.listener;

import org.aspectj.lang.ProceedingJoinPoint;

import com.wms.core.exceptions.WMSException;

/**
 * 保存实体后监听器接口
 *
 * @author xb
 *
 */
public interface IEntityAfterSavingListener {
	/**
	 * 执行处理
	 *
	 * @param entity
	 */
	boolean processSaved(Object entity, ProceedingJoinPoint proceedingJoinPoint)
			throws WMSException;
}
