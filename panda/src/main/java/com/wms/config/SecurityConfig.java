package com.wms.config;

import com.wms.core.exceptions.WMSException;
import com.wms.core.security.*;
import com.wms.core.utils.applicationcontext.ApplicationContextUtil;
import com.wms.core.utils.common.ObjectUtils;
import com.wms.core.utils.jdbc.IJdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.AuthorizationContext;

import java.util.List;
import java.util.Map;


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Import(ApplicationConfig.class)
public class SecurityConfig {
	@Autowired
	ApplicationConfig applicationConfig;

	@Bean
	public MapReactiveUserDetailsService userDetailsService() throws Exception{
		return new MapReactiveUserDetailsService(new UserDetailsUtil().getUsers(applicationConfig.jdbcDao()));
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception{
		AuthenticationWebFilter authentication =
				new AuthenticationWebFilter(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService()));
		authentication.setAuthenticationConverter(new ServerMonoAuthenticationConverter());
		http
				.authorizeExchange()
				.pathMatchers("/**/*.jpg", "/**/*.png", "/**/*.gif",
				"/**/*.css", "/**/*.js", "/**/*.otf", "/**/*.eot", "/**/*.ttf",
				"/**/*.woff", "/**/*.woff2", "/**/*.svg", "/**/*.doc",
				"/**/*.docx", "/**/*.xls", "/**/*.xlsx", "/**/*.ppt",
				"/**/*.pptx", "/**/*.fla", "/**/*.jpeg", "/**/*.avi",
				"/**/*.bmp", "/**/*.eml", "/**/*.eps", "/**/*.html",
				"/**/*.ini", "/**/*.ind", "/**/*.jsf", "/**/*.midi",
				"/**/*.mov", "/**/*.mp3", "/**/*.mpeg", "/**/*.pdf",
				"/**/*.proj", "/**/*.psg", "/**/*.pst", "/**/*.pub",
				"/**/*.rar", "/**/*.tiff", "/**/*.txt", "/**/*.url",
				"/**/*.vsd", "/**/*.wav", "/**/*.wma", "/**/*.wmv",
				"/**/*.dll", "/**/*.jnilib", "/**/*.dylib", "/**/*.so",
				"/**/*.zip", "/**/*.json","/login","/favicon.ico")
				.permitAll()
				.and()
				.addFilterAt(authentication, SecurityWebFiltersOrder.HTTP_BASIC)
				.authorizeExchange()
				.pathMatchers("/console/*")
				.authenticated().and()
				.formLogin().loginPage("/login")
				.authenticationSuccessHandler(new WebFilterChainServerJWTAuthenticationSuccessHandler())
				.and()
				.csrf().disable()
				.logout();
				http.authorizeExchange()
						.pathMatchers("/**")
						.access((mono,context) -> mono.map(auth -> auth.getAuthorities().stream()
								.filter(e -> this.pageInvocationFilter((GrantedAuthority)e,context)).count()>0).map(AuthorizationDecision::new));
		return http.build();
	}

	private boolean pageInvocationFilter(GrantedAuthority ga, AuthorizationContext context){
		String authority = ga.getAuthority();
		IJdbcDao jdbcDao = (IJdbcDao)new ApplicationContextUtil().getApplicationContext().getBean("jdbcDao");
		final String queryPageInvocationsStatement = "select * from t_pageinvocations where url = '?'";
		Map<String,Object> pageInvocation = null;
		try{pageInvocation = jdbcDao.queryForMap(queryPageInvocationsStatement.replace("?",context.getExchange().getRequest().getURI().getPath()));}catch (Exception e){}
		String roles = null;
		if(ObjectUtils.isNotEmpty(pageInvocation)) {
			roles = pageInvocation.get("roles").toString();
				for(String role : roles.split(",")){
					if(authority.equals(role)){
						return true;
					}
				}
		}
		return false;
	}
}
