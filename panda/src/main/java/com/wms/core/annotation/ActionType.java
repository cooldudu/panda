package com.wms.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述树形实体字段的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)  
public @interface ActionType {
	public String actionName(); //树形字段类型
	public String actionUrl();
	public String dependUrls();
}
