package com.wms.core.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Aspect
@Component
public class GetMappingInterceptor {

	@Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public Object doExecute(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {
			String userName = InterceptUtil.getCurrentUserName(proceedingJoinPoint);
			Object returnValue = proceedingJoinPoint.proceed();
			return returnValue;
	}
}
