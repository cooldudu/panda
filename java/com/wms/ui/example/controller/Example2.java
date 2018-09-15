package com.wms.ui.example.controller;

import com.wms.core.annotation.MakeLog;
import com.wms.core.annotation.Token;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/manage")
public class Example2 {

    @GetMapping("/index")
    @MakeLog(logContent="have test ${principal.getName} split ${exchange.getRequest.getHeaders.getHost.getHostName}")
    @Token
    public Mono<String> hello(ServerWebExchange exchange, Principal principal) {
        return Mono.just(principal.getName());
    }
}