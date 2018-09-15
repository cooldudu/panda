package com.wms.ui.controller;

import com.wms.core.utils.authority.AuthorityUtil;
import static com.wms.core.utils.common.StaticData.CAN_MENU_SUBMIT;

import com.wms.core.utils.common.ObjectUtils;
import com.wms.core.utils.operator.OperatorUtil;
import com.wms.domain.DiagramRepo;
import com.wms.domain.MenuRepo;
import com.wms.domain.PageInvocationRepo;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import play.api.libs.json.Json;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@RestController
@RequestMapping(value="menu",name="菜单管理")
public class MenuController {
    @GetMapping(value = "get",name="菜单列表")
    public Mono<ResponseEntity<List<String>>> getMenu(@AuthenticationPrincipal UserDetails userDetails){
        return new MenuRepo().findAllMenu().filter(r -> r.url().isEmpty()||(r.url().nonEmpty()&&AuthorityUtil.hasAuthority(r.url().get(),AuthorityUtil.getPersonalAuthorities(userDetails).get("urls"))))
                .map(r -> Json.toJson(r,new MenuRepo().menuFormat()).toString()).collectList()
                .flatMap(
                        r -> Mono.just(new ResponseEntity<>(r,HttpStatus.CREATED))
                );
    }

    @GetMapping(value = "getbuttons")
    public Set<HashMap<String,String>> getButtons(@AuthenticationPrincipal UserDetails userDetails){
        var result = new HashSet<HashMap<String,String>>();
        var buttons = AuthorityUtil.getPersonalAuthorities(userDetails).get("buttons");
        var authorities = userDetails.getAuthorities();
        var isAdmin = false;
        for(var authority:authorities){
            var piOpt = new PageInvocationRepo().findByEntityTypeAndName("role",authority.getAuthority().replace("ROLE_","")).block();
            if(piOpt.nonEmpty()){
                var urls = piOpt.get().urls();
                if(ObjectUtils.isNotEmpty(urls)&&urls.equals("/**")){
                    isAdmin = true;
                    break;
                }
            }
        }
        if(isAdmin){
            return OperatorUtil.findOperatorDatas();
        }else{
            var btnArray = new JSONArray(buttons);
            for(var i=0;i<btnArray.length();i++){
                var obj = btnArray.getJSONObject(i);
                var btnName = obj.getString("name");
                var menuUid = obj.getString("menuUid");
                var menu =OperatorUtil.findOperatorDataByMenuUidAndMethodName(menuUid,btnName);
                if(ObjectUtils.isNotEmpty(menu)) {
                    result.add(menu);
                }
            }
            return result;
        }

    }

    @PostMapping(value="save",name="保存菜单")
    public Mono<String> saveMenu(@RequestBody String content) throws UnsupportedEncodingException {
        if(CAN_MENU_SUBMIT) {
            CAN_MENU_SUBMIT = false;
            var result = URLDecoder.decode(content, "UTF-8").replaceAll("\n", "");
            var diagram = result.split("&history=")[0].replace("content=", "");
            var action = result.split("&history=")[1];
            new DiagramRepo().updateMenuDiagramContent(diagram);
            return new MenuRepo().saveMenu(action).flatMap(r-> Mono.just("success")).onErrorReturn("error").doFinally(r ->CAN_MENU_SUBMIT=true);
        }else{
            return Mono.just("can not submit");
        }
    }
}
