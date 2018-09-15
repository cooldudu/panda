package com.wms.ui.controller;

import com.wms.domain.PageInvocationRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import play.api.libs.json.Json;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="pageinvocation",name="权限管理")
public class PageInvocationController {
    @GetMapping(value = "edit")
    public Mono<String> edit(@RequestParam(name = "entityType",defaultValue = "role")String entityType,
                             @RequestParam(name = "entityName",defaultValue = "")String entityName,
                             @RequestParam(name = "obj",defaultValue = "")String obj){
        var json = Json.parse(obj.replaceAll("\n",""));
        var pageInvocation = new PageInvocationRepo().findByEntityTypeAndName(entityType,entityName).block();
        if(pageInvocation.nonEmpty()){
           return new PageInvocationRepo().editUrl(pageInvocation.get(),json).then(Mono.just("success"));
        }else{
           return new PageInvocationRepo().createPageInvocation(entityType,entityName,json).then(Mono.just("success"));
        }
    }
}
