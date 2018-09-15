package com.wms.config;

import com.wms.core.security.*;
import com.wms.core.utils.authority.AuthorityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.AuthorizationContext;

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
		var authentication =
				new AuthenticationWebFilter(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService()));
		http
				.authorizeExchange()
				.pathMatchers("/console/login","/console/login/**","/favicon.ico","/token/get")
				.permitAll()
				.and()
				.addFilterAt(authentication, SecurityWebFiltersOrder.HTTP_BASIC)
				.authorizeExchange()
				.pathMatchers("/console/**")
				.authenticated().and()
				.formLogin().loginPage("/console/login")
				.and()
				.csrf().disable()
				.logout().logoutUrl("/logout");
				http.authorizeExchange()
						.pathMatchers("/**")
						.access((mono,context) -> mono.map(auth -> auth.getAuthorities().stream()
								.filter(e -> this.pageInvocationFilter(auth,context)).count()>0).map(AuthorizationDecision::new));
		return http.build();
	}

	private boolean pageInvocationFilter(Authentication auth, AuthorizationContext context){
		var result = false;
		try {
			var currentUrl = context.getExchange().getRequest().getURI().getPath();
			result = AuthorityUtil.hasAuthority(currentUrl,AuthorityUtil.getPersonalAuthorities(auth).get("urls"));
		}catch (Exception ex){
			System.out.println(ex.getMessage());
		}

		return result;
	}
}
