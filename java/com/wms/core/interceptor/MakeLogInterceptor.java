package com.wms.core.interceptor;

import com.wms.core.utils.common.ObjectUtils;
import com.wms.core.utils.common.StaticData;
import com.wms.core.utils.datetime.DateTimeUtil;
import com.wms.core.utils.request.MacUtil;
import com.wms.core.utils.request.RequestUtil;
import com.wms.domain.Log;
import com.wms.domain.LogRepo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import scala.Option;

import java.lang.reflect.Method;
import java.sql.Timestamp;

@Aspect
@Component
public class MakeLogInterceptor {
    @Around("@annotation(com.wms.core.annotation.MakeLog)")
    public Object doExecute(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        Object returnValue = null;
        Timestamp begin = null;
        Timestamp end = null;
        Class<?> clazz = null;
        Method method = null;
        var userName = InterceptUtil.getCurrentUserName(proceedingJoinPoint);
        if (StaticData.LOG_ENABLED && !StringUtils.isEmpty(userName)) {
            var signature = (MethodSignature) proceedingJoinPoint
                    .getSignature();
            clazz = proceedingJoinPoint.getTarget().getClass();
            method = signature.getMethod();
            begin = new Timestamp(DateTimeUtil.getNow().getTime());
        }
        returnValue = proceedingJoinPoint.proceed();
        if (StaticData.LOG_ENABLED && ObjectUtils.isNotEmpty(userName)) {
            end = new Timestamp(DateTimeUtil.getNow().getTime());
            var spend = end.getTime() - begin.getTime();
            ServerWebExchange request = null;
            for (var object : proceedingJoinPoint.getArgs()) {
                if (object instanceof ServerWebExchange) {
                    request = (ServerWebExchange) object;
                }
            }
            if (ObjectUtils.isNotEmpty(returnValue)
                    && ObjectUtils.isNotEmpty(request)) {
                if(ObjectUtils.isNotEmpty(InterceptUtil.getOperaterDesc(proceedingJoinPoint))) {
                    var log = new Log(begin, end, RequestUtil.getBrowser(request), RequestUtil.getOs(request),
                            spend, clazz.getName(), method.getName(), MacUtil.getIpAddr(request),
                            userName, InterceptUtil.getOperaterDesc(proceedingJoinPoint), Option.empty());
                    new LogRepo().insertLog(log);
                }
            }
        }
        return returnValue;
    }

}
