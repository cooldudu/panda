package com.wms.ui.controller;

import com.wms.core.utils.common.ObjectUtils;
import com.wms.core.utils.uuid.UUIDUtil;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "token")
public class TokenController{
	@GetMapping(value = "get")
	@ResponseBody
	public Mono<String> getToken(ServerWebExchange exchange){
		return exchange.getSession().map(session ->{
			var token = session.getAttribute("request_token");
			if(ObjectUtils.isNotEmpty(token)){
				return token.toString();
			}else{
				var strGUID = UUIDUtil.generateShortUuid();
				session.getAttributes().put("request_token",strGUID);
				return strGUID;
			}
		});
	}
}
