package com.wms.ui.controller;

import static com.wms.core.utils.common.StaticData.DEFAULT_PAGE_SIZE;
import static com.wms.core.utils.common.StaticData.FIRST_PAGE_NUM;

import com.wms.domain.PageInvocationRepo;
import com.wms.domain.Role;
import com.wms.domain.RoleRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import play.api.libs.json.Json;
import reactor.core.publisher.Mono;
import scala.Int;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping(value="role",name="角色管理")
public class RoleController {
    @GetMapping(value = "get",name="角色列表")
    public Mono<ResponseEntity<List<String>>> getRole(Mono<Principal> principalMono,
                                                      @RequestParam(name = "page", defaultValue = FIRST_PAGE_NUM) int page,
                                                      @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
                                                      @RequestParam(name = "rolename", defaultValue = "") String roleName,
                                                      @RequestParam(name = "roletype", defaultValue = "") String roleType
                                                      ){
        return new RoleRepo().findAllByPageable(roleName,roleType,PageRequest.of(page,size)).map(r -> Json.toJson(r,new RoleRepo().roleFormat()).toString()).collectList()
                .flatMap(r-> Mono.just(new ResponseEntity<>(r, HttpStatus.CREATED)));
    }

    @GetMapping(value = "gettotal")
    public Mono<Object> getRoleTotal(Mono<Principal> principalMono,
                                     @RequestParam(name = "rolename", defaultValue = "") String roleName,
                                     @RequestParam(name = "roletype", defaultValue = "") String roleType){
        return new RoleRepo().findTotalNum(roleName,roleType);
    }

    @PostMapping(value="save",consumes = "application/json",name="保存角色")
    public Mono<String> save(@RequestBody String content) throws UnsupportedEncodingException {
        var result = URLDecoder.decode(content, "UTF-8").replaceAll("\n", "");
        return new RoleRepo().save(result).then(Mono.just("success"));
    }

    @GetMapping(value="del",name="删除角色")
    public Mono<Object> delete(Mono<Principal> principalMono,
                            @RequestParam(name = "id", defaultValue = "") int id){
        return new RoleRepo().delete(id);
    }

    @GetMapping(value = "getbyrole",name="角色授权")
    public String getByRole(@RequestParam(name="entityName")String entityName){
        var piOpt =  new PageInvocationRepo().findByEntityTypeAndName("role",entityName).block();
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
