package com.wms.core.utils.authority;

import com.wms.core.utils.common.ObjectUtils;
import com.wms.core.utils.common.StaticData;
import com.wms.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import scala.Option;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AuthorityUtil {
    public static Map<String,String> getPersonalAuthorities(Object auth){
        Map<String,String> result = new HashMap<>();
        String userName =  null;
        Collection<?> authorities = null;
        if(auth instanceof Authentication){
            var authority = (Authentication) auth;
            userName = authority.getName();
            authorities = authority.getAuthorities();
        } else if(auth instanceof UserDetails){
            var userDetails = (UserDetails)auth;
            userName = userDetails.getUsername();
            authorities = userDetails.getAuthorities();
        }
        var urls = "";// StaticData.PERSONAL_PREV.get(userName);
        var buttons = "";
        if(ObjectUtils.isEmpty(urls)){
            var urlsb = new StringBuilder(";");
            var buttonsb = new StringBuilder();
            for (var authority : authorities) {
                var rolePIOpt = new PageInvocationRepo().findByEntityTypeAndName("role", authority.toString().substring("ROLE_".length())).block();
                AuthorityUtil.appendUrlAndButton(rolePIOpt,urlsb,buttonsb);
            }
            var userPIOpt = new PageInvocationRepo().findByEntityTypeAndName("user",userName).block();
            AuthorityUtil.appendUrlAndButton(userPIOpt,urlsb,buttonsb);

            var relegations = new RelegationRepo().findByAccountId(userName).collectList().block();
            for(var relegation:relegations){
                var department = new DepartmentRepo().findById(relegation.deptId()).block();
                if(department.nonEmpty()) {
                    var deptPIOpt = new PageInvocationRepo().findByEntityTypeAndName("department", department.get().name()).block();
                    AuthorityUtil.appendUrlAndButton(deptPIOpt, urlsb,buttonsb);
                }
            }
            urls = urlsb.toString();
            buttons = "["+buttonsb.substring(0,buttonsb.length()-1)+"]";
            //StaticData.PERSONAL_PREV.put(userName,urls);
        }
        result.put("urls",urls);
        result.put("buttons",buttons);
        return result;
    }

    private static void appendUrlAndButton(Option<PageInvocation> piOpt, StringBuilder urlsb, StringBuilder buttonsb){
        if(piOpt.nonEmpty()){
            var pageInvocation = piOpt.get();
            if (ObjectUtils.isNotEmpty(pageInvocation)) {
                if(pageInvocation.urls().endsWith(";")){
                    urlsb.append(pageInvocation.urls());
                }else{
                    urlsb.append(pageInvocation.urls()+";");
                }
                var buttons = pageInvocation.buttonIds();
                if(ObjectUtils.isNotEmpty(buttons)){
                    buttons = buttons.replace("[","");
                    buttons = buttons.replace("]",",");
                    buttonsb.append(buttons);
                }
            }
        }
    }

    public static boolean hasAuthority(String url,String urls){
        var result = false;
        if(ObjectUtils.isNotEmpty(url)) {
            if (urls.indexOf(";" + url + ";") != -1) {
                result = true;
            } else {
                for (var pattern : urls.split(";")) {
                    var matcher = new AntPathMatcher();
                    if (matcher.match(pattern, url)) {
                        result = true;
                        break;
                    }
                }
            }
        }else{
            result = true;
        }
        return result;
    }
}
