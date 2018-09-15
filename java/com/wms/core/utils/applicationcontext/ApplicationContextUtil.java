package com.wms.core.utils.applicationcontext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

/**
 * 通过spring的bean的名称，获取spring中的容器对象
 * @author xb
 *
 */
@Repository
public class ApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;

	/** Creates a new instance of ApplicationContextUtil */
	public ApplicationContextUtil() {
	}

	public static void setApplicationContextStatic(ApplicationContext applicationContext){
		ApplicationContextUtil.applicationContext = applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		setApplicationContextStatic(applicationContext);
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
