package com.wms.core.utils.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 统一验证工具
 * @author dudu
 *
 * @param <T>
 */
public class ValidatorUtil<T> {
	/**
	 * 实体验证
	 * @param name 验证的字段名称
	 * @return
	 */
	public String validate(T entity,String name){
		var factory = Validation.buildDefaultValidatorFactory();
		var validator = factory.getValidator();
		var constraintViolations = validator.validate(entity);
		if(constraintViolations.size()>0){
			var result = new StringBuffer("实体 "+name+" 存在异常如下    ");
			for(var constraintViolation : constraintViolations){
				result.append("字段："+constraintViolation.getPropertyPath()+"  异常："+constraintViolation.getMessage()+",");
			}
			return result.toString();
		}else{
			return null;
		}
	}
}
