package com.wms.ui.controller;

import com.wms.domain.DepartmentRepo;
import com.wms.domain.PageInvocationRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import play.api.libs.json.Json;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@RestController
@RequestMapping(value="department",name="组织部门")
public class DepartmentController {
    @GetMapping(value = "getfortree",name="部门管理")
    public Mono<ResponseEntity<List<String>>> getDepartment(
            @RequestParam(name = "companyId") int companyId){
            return new DepartmentRepo().findByCompanyId(companyId)
                    .map(r -> Json.toJson(r, new DepartmentRepo().departmentFormat()).toString()).collectList()
                    .flatMap(r -> Mono.just(new ResponseEntity<>(r, HttpStatus.CREATED)));
    }

    @GetMapping(value="drag")
    public void saveDrag( @RequestParam(name = "dragId") int dragId,
                          @RequestParam(name = "dropId") int dropId,
                          @RequestParam(name = "type") String type){
        new DepartmentRepo().setDragInfo(dragId,dropId,type);
    }

    @PostMapping(value = "save",consumes = "application/json",name="新建部门")
    public Mono<String> save(@RequestBody String content)throws UnsupportedEncodingException{
        var result = URLDecoder.decode(content,"UTF-8").replaceAll("\n","");
        return new DepartmentRepo().save(result).then(Mono.just("success"));
    }

    @GetMapping(value="del",name="删除部门")
    public Mono<Object> delete(@RequestParam(name="id") int id){
        return new DepartmentRepo().delete(id);
    }

    @GetMapping(value="makelevel")
    public String makeLevel(@RequestParam(name="parentLevel")String parentLevel){
        return new DepartmentRepo().makeLevel(parentLevel);
    }

    @GetMapping(value="edit",name="修改部门")
    public Mono<Object> edit(@RequestParam(name="id") int id,
                             @RequestParam(name="name") String name,
                             @RequestParam(name="descript") String descript){
        return new DepartmentRepo().edit(id,name,descript);
    }

    @GetMapping(value = "getbydepartment",name="部门授权")
    public String getByDepartment(@RequestParam(name="entityName")String entityName){
        var piOpt =  new PageInvocationRepo().findByEntityTypeAndName("department",entityName).block();
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
