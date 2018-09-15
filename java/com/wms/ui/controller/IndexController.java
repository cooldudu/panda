package com.wms.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;

@Controller
@RequestMapping(value="console",name="后台管理")
public class IndexController {

    @GetMapping(value="index")
    public String index() {
        return "index";
    }

    @GetMapping(value="default")
    public String defaultPage(ServerWebExchange exchange) {
        enableFrame(exchange);
        return "default";
    }

    @GetMapping(value="menu",name="菜单管理")
    public String menu(ServerWebExchange exchange){
        enableFrame(exchange);
        return "menu/index";
    }

    @GetMapping(value="role",name="角色管理")
    public String role(ServerWebExchange exchange){
        enableFrame(exchange);
        return "role/index";
    }

    @GetMapping(value="company",name="组织管理")
    public String company(ServerWebExchange exchange){
        enableFrame(exchange);
        return "company/index";
    }

    @GetMapping(value="user",name="用户管理")
    public String user(ServerWebExchange exchange){
        enableFrame(exchange);
        return "user/index";
    }

    private void enableFrame(ServerWebExchange exchange){
        exchange.getResponse().getHeaders().remove("X-Frame-Options");
        exchange.getResponse().getHeaders().add("X-Frame-Options", "AllowAll");
    }
}