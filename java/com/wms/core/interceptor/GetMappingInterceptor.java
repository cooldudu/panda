package com.wms.core.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class GetMappingInterceptor {

	@Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public Object doExecute(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {
			var returnValue = proceedingJoinPoint.proceed();
			return returnValue;
	}
}
