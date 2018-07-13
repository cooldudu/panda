package org.springsecurity.interceptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.wms.core.exceptions.WMSException;
import com.wms.core.utils.jdbc.IJdbcDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.wms.core.utils.common.ObjectUtils;

public class MySecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	protected final Log logger = LogFactory.getLog(getClass());

	private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;
	private List<Map<String,Object>> rolelist=new ArrayList();

	public Map<RequestMatcher, Collection<ConfigAttribute>> getRequestMap() {
		return requestMap;
	}
	public void setRequestMap(Map<RequestMatcher, Collection<ConfigAttribute>> requestMap) {
		this.requestMap = requestMap;
	}

	@Resource
	private IJdbcDao jdbcDao;

	public void init() {
		try {
			Map<RequestMatcher, Collection<ConfigAttribute>> result = new HashMap<RequestMatcher, Collection<ConfigAttribute>>();
			rolelist = jdbcDao.queryForList("select * from t_roles");
			List<Map<Integer,Object>> pageInvocations = jdbcDao.queryForList("select * from t_pageinvocations");
			if(ObjectUtils.isNotEmpty(pageInvocations)){
				for(Map<Integer,Object> pageInvocation : pageInvocations){
					RequestMatcher requestMatcher = new AntPathRequestMatcher(
							pageInvocation.get("url").toString());
					String[] roles = pageInvocation.get("roles").toString().split(",");
					Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
					for (String role : roles) {
						configAttributes.add(new SecurityConfig(role));
					}
					//key--url和value--角色
					result.put(requestMatcher, configAttributes);
				}
				requestMap = result;
			}else {
				requestMap = new HashMap<RequestMatcher, Collection<ConfigAttribute>>();
			}
		}catch (WMSException e){
			e.printStackTrace();
		}
	}
	 /**
     *启动加载
     */
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
		//初始化
		if (null == requestMap || requestMap.size() <= 0) {
			init();
		}
		//赋值
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
				.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}

		return allAttributes;
	}
    /**
     * 每次访问都要经过
     */
	public Collection<ConfigAttribute> getAttributes(Object object) {
		Collection<ConfigAttribute> results = new ArrayList<ConfigAttribute>();
		final HttpServletRequest request = ((FilterInvocation) object)
				.getRequest();
		//初始化
		if (null == requestMap || requestMap.size() <= 0) {
			init();
		}
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
				.entrySet()) {
			if (entry.getKey().matches(request)) {
				return entry.getValue();
			}
		}
		if(rolelist.size()>0){
			for(Map<String,Object> role:rolelist){
				results.add(new SecurityConfig(role.get("name").toString()));
			}
				
		}else{
			results.add(new SecurityConfig("ROLE_USER"));
		}
		return results;
	}

	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}


}
