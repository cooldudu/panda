package com.wms.ui.controller;

import com.wms.domain.CompanyRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import play.api.libs.json.Json;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.List;

import static com.wms.core.utils.common.StaticData.DEFAULT_PAGE_SIZE;
import static com.wms.core.utils.common.StaticData.FIRST_PAGE_NUM;

@RestController
@RequestMapping(value="company",name="组织部门")
public class CompanyController {
    @GetMapping(value = "get",name="组织列表")
    public Mono<ResponseEntity<List<String>>> getCompany(Mono<Principal> principalMono,
                                                      @RequestParam(name = "page", defaultValue = FIRST_PAGE_NUM) int page,
                                                      @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
                                                      @RequestParam(name = "name", defaultValue = "") String name){
        return new CompanyRepo().findAllByPageable(name, PageRequest.of(page,size)).map(r -> Json.toJson(r,new CompanyRepo().companyFormat()).toString()).collectList()
                .flatMap(r-> Mono.just(new ResponseEntity<>(r, HttpStatus.CREATED)));
    }

    @GetMapping(value = "gettotal")
    public Mono<Object> getRoleTotal(Mono<Principal> principalMono,
                                     @RequestParam(name = "name", defaultValue = "") String name){
        return new CompanyRepo().findTotalNum(name);
    }

    @PostMapping(value="save",consumes = "application/json",name="保存组织")
    public Mono<String> save(@RequestBody String content) throws UnsupportedEncodingException {
        var result = URLDecoder.decode(content,"UTF-8").replaceAll("\n", "");
        return new CompanyRepo().save(result).then(Mono.just("success"));
    }

    @GetMapping(value="del",name="删除组织")
    public Mono<Object> delete(Mono<Principal> principalMono,
                               @RequestParam(name = "id", defaultValue = "") int id){
        return new CompanyRepo().delete(id);
    }
}
