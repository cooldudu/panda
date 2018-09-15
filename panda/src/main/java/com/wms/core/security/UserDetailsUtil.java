package com.wms.core.security;

import com.wms.core.utils.common.ObjectUtils;
import com.wms.core.utils.jdbc.IJdbcDao;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

public class UserDetailsUtil {

    public Collection<UserDetails> getUsers(IJdbcDao jdbcDao) throws Exception{
        List<Map<String,Object>> userRs = null;
        List<Map<String,Object>> authorityRs = null;
        List<String> roles = new ArrayList<String>();
        Collection<UserDetails> users = new HashSet<UserDetails>();
        final var getUserListQuery = "select * from t_accounts";
        final var getAuthoritiesByUsernameQuery = "select u.username,r.rolename from t_authorities a inner join t_accounts u on a.account_id=u.id inner join t_roles r on a.role_id=r.id where u.username= '?'";

        userRs = jdbcDao.queryForList(getUserListQuery);
        if(ObjectUtils.isNotEmpty(userRs)){
            for(var user :userRs){
                var userName = user.get("username").toString();
                authorityRs = jdbcDao.queryForList(getAuthoritiesByUsernameQuery.replace("?",userName));
                for(var authority : authorityRs){
                    roles.add(authority.get("rolename").toString());
                }
                var ud = User.builder().username(userName).password(user.get("password").toString())
                        .disabled((Boolean) user.get("enabled")).accountExpired((Boolean)user.get("isAccoumtNonExpired"))
                        .accountLocked((Boolean) user.get("isAccountNonLocked"))
                        .credentialsExpired((Boolean) user.get("isCredentialsNonExpired"))
                        .roles(roles.toArray(new String[roles.size()]))
                        .passwordEncoder(password ->  "{bcrypt}"+ password)
                        .build();
                users.add(ud);
            }
        }
        return users;
    }
}
