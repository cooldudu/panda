package com.wms.core.listener;

import org.aspectj.lang.ProceedingJoinPoint;

import com.wms.core.exceptions.WMSException;

/**
 * 保存实体前监听器接口
 *
 * @author xb
 *
 */
public interface IEntityBeforeSavingListener {
	/**
	 * 执行处理
	 *
	 * @param entity
	 */
	boolean processPrepareSave(Object entity,
                               ProceedingJoinPoint proceedingJoinPoint) throws WMSException;
}
