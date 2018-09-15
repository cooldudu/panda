package com.wms.core.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wms.core.utils.regexp.RegExp;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.userdetails.UserDetails;

import com.wms.core.annotation.MakeLog;
import com.wms.core.utils.common.ObjectUtils;
import com.wms.core.utils.common.StringUtil;
import org.springframework.web.server.ServerWebExchange;

public class InterceptUtil {
    public static Object getMethodArg(ProceedingJoinPoint proceedingJoinPoint,
                                      int index) throws IllegalArgumentException, SecurityException,
            IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        var args = proceedingJoinPoint.getArgs();
        return args[index - 1];
    }

    public static void setMethodArg(ProceedingJoinPoint proceedingJoinPoint,
                                    int index, Object value) throws IllegalArgumentException,
            SecurityException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        var args = proceedingJoinPoint.getArgs();
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
            ProceedingJoinPoint proceedingJoinPoint){
        var args = proceedingJoinPoint.getArgs();
        for (var arg : args) {
            if (arg instanceof Principal) {
                var principal = (Principal) arg;
                return principal.getName();
            } else if (arg instanceof UserDetails) {
                var userDetails = (UserDetails) arg;
                return userDetails.getUsername();
            }
        }
        return "";
    }

    public static ServerWebExchange getExchange(ProceedingJoinPoint proceedingJoinPoint)throws Exception{
        var args = proceedingJoinPoint.getArgs();
        for(var arg:args){
            if(arg instanceof ServerWebExchange){
                return (ServerWebExchange)arg;
            }
        }
        return null;
    }

    public static String getOperaterDesc(ProceedingJoinPoint proceedingJoinPoint){
        var signature = (MethodSignature) proceedingJoinPoint
                .getSignature();
        var method = signature.getMethod();
        var annotation = method
                .getAnnotation(MakeLog.class);
        if (ObjectUtils.isNotEmpty(annotation)) {
            var logContent = annotation.logContent();
            try {
                var args = proceedingJoinPoint.getArgs();
                var names = signature.getParameterNames();
                Map<String, Object> params = new HashMap<String, Object>();
                for (var i = 0; i < names.length; i++) {
                    params.put(names[i], args[i]);
                }
                var reg = "(?<=(?<!\\\\)\\$\\{)(.*?)(?=(?<!\\\\)\\})";
                var re = new RegExp();
                var list = re.find(reg, logContent);
                for (var key : list) {
                    Object value = null;
                    if (key.indexOf(".") == -1) {
                        value = params.get(key);
                    } else {
                        var keys = key.split("\\.");
                        value = params.get(keys[0]);
                        for (var j = 1; j < keys.length; j++) {
                            if (keys[j].indexOf("(") != -1 || keys[j].indexOf(")") != -1) {
                                throw new Exception("You needn't write () on function.");
                            }
                            var tmp = value.getClass().getMethod(keys[j]);
                            value = tmp.invoke(value);
                        }
                    }
                    logContent = logContent.replace("${" + key + "}", value.toString());
                }
            } catch (Exception ex) {
            }
            return logContent;
        }
        return "";
    }

}
