package com.wms.core.interceptor;

import com.wms.core.utils.common.ObjectUtils;
import com.wms.core.utils.uuid.UUIDUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Aspect
@Order(5)
@Component
public class TokenInterceptor {
	@Around("@annotation(com.wms.core.annotation.Token)")
	public Object doExecute(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {
			var exchange = InterceptUtil.getExchange(proceedingJoinPoint);
			if (ObjectUtils.isNotEmpty(exchange)) {
				return exchange.getSession().map(session -> {
					try {
					var strGUID = UUIDUtil.generateShortUuid();
					var token = exchange.getRequest().getQueryParams().getFirst("token");
					var request_token = session.getAttribute("request_token").toString();
					if(ObjectUtils.isNotEmpty(token)&&ObjectUtils.isNotEmpty(request_token)&&token.equals(request_token)){
							session.getAttributes().put("request_token",strGUID);
							return proceedingJoinPoint.proceed();
					}
					}catch (Throwable t){}
					return Mono.empty();
				}).block();
			}else{
				return "";
			}
	}
}
