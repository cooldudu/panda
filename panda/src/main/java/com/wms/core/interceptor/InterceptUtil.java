package com.wms.core.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.wms.core.annotation.UserOperateInfo;
import com.wms.core.utils.common.ObjectUtils;
import com.wms.core.utils.common.StringUtil;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class InterceptUtil {
	public static Object getMethodArg(ProceedingJoinPoint proceedingJoinPoint,
									  int index) throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Object[] args = proceedingJoinPoint.getArgs();
		return args[index - 1];
	}

	public static void setMethodArg(ProceedingJoinPoint proceedingJoinPoint,
									int index, Object value) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Object[] args = proceedingJoinPoint.getArgs();
		args[index - 1] = value;
	}

	public static Object getActionField(
			ProceedingJoinPoint proceedingJoinPoint, String fieldName)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		return proceedingJoinPoint.getTarget().getClass()
				.getMethod(StringUtil.queryGetMethodString(fieldName))
				.invoke(proceedingJoinPoint.getTarget());
	}

	public static void setActionField(ProceedingJoinPoint proceedingJoinPoint,
									  String fieldName, Object... value) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		proceedingJoinPoint
				.getTarget()
				.getClass()
				.getMethod(
						StringUtil.querySetMethodString(fieldName),
						proceedingJoinPoint
								.getTarget()
								.getClass()
								.getMethod(
										StringUtil
												.queryGetMethodString(fieldName))
								.getReturnType())
				.invoke(proceedingJoinPoint.getTarget(), value);

	}

	public static String getCurrentUserName(
			ProceedingJoinPoint proceedingJoinPoint)
			throws IllegalArgumentException, SecurityException{
		Object[] args = proceedingJoinPoint.getArgs();
		for(Object arg:args){
			if(arg instanceof Principal){
				Principal principal = (Principal)arg;
				return principal.getName();
			}else if(arg instanceof UserDetails){
				UserDetails userDetails = (UserDetails)arg;
				return userDetails.getUsername();
			}
		}
		return "";
	}

	public static String getOperaterDesc(ProceedingJoinPoint proceedingJoinPoint)
			throws SecurityException, ClassNotFoundException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		MethodSignature signature = (MethodSignature) proceedingJoinPoint
				.getSignature();
		Method method = signature.getMethod();
		// Class<?> clazz = proceedingJoinPoint.getTarget().getClass();
		Object[] args = proceedingJoinPoint.getArgs();
		UserOperateInfo annotation = (UserOperateInfo) method
				.getAnnotation(UserOperateInfo.class);
		if (ObjectUtils.isNotEmpty(annotation)) {
			String operaterDesc = annotation.operateDesc();
			String[] operateVars = annotation.operateVars();
			for (String var : operateVars) {
				if (!StringUtils.isEmpty(var)) {
					Object value = args[Integer.parseInt(var) - 1];
					if (!ObjectUtils.isEmpty(value)) {
						operaterDesc = operaterDesc.replaceFirst("\\$",
								value.toString());
					}
				}
			}
			return operaterDesc;
		}
		return "";
	}

}
