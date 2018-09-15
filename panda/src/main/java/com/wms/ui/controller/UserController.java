package com.wms.ui.controller;

import com.wms.domain.PageInvocationRepo;
import com.wms.domain.UserRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import play.api.libs.json.Json;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static com.wms.core.utils.common.StaticData.DEFAULT_PAGE_SIZE;
import static com.wms.core.utils.common.StaticData.FIRST_PAGE_NUM;

@RestController
@RequestMapping(value="user",name="用户管理")
public class UserController {
    @GetMapping(value="get",name="用户列表")
    public Mono<ResponseEntity<List<String>>> getUser(@RequestParam(name = "page", defaultValue = FIRST_PAGE_NUM) int page,
                                                      @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
                                                      @RequestParam(name = "userName", defaultValue = "") String userName){
        return new UserRepo().findByPageable(userName,PageRequest.of(page,size)).map(r -> Json.toJson(r,new UserRepo().userFormat()).toString())
                .collectList().flatMap(r->Mono.just(new ResponseEntity<>(r, HttpStatus.CREATED)));
    }

    @GetMapping(value="gettotal")
    public Mono<Object> getUserTotal(@RequestParam(name = "userName", defaultValue = "") String userName){
        return new UserRepo().findTotalNum(userName);
    }

    @PostMapping(value="/save",consumes = "application/json",name="保存用户")
    public Mono<String> save(@RequestBody String content) throws UnsupportedEncodingException {
        var result = URLDecoder.decode(content, "UTF-8").replaceAll("\n", "");
        return new UserRepo().save(result).then(Mono.just("success"));
    }

    @GetMapping(value="del",name="删除用户")
    public Mono<Object> delete(@RequestParam(name = "id", defaultValue = "") int id){
        return new UserRepo().delete(id);
    }

    @GetMapping(value = "getbyuser",name="个人授权")
    public String getByUser(@RequestParam(name="entityName")String entityName){
        var piOpt =  new PageInvocationRepo().findByEntityTypeAndName("user",entityName).block();
        var buttonIds = "";
        var menuIds = "";
        if(piOpt.nonEmpty()){
            buttonIds = piOpt.get().buttonIds();
            menuIds = piOpt.get().menuIds();
        }
        if(piOpt.nonEmpty()){
            return menuIds+";"+buttonIds;
        }else{
            return "";
        }
    }
}