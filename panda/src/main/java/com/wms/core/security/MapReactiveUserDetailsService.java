package com.wms.core.security;

import com.wms.core.exceptions.WMSException;
import com.wms.core.utils.applicationcontext.ApplicationContextUtil;
import com.wms.core.utils.common.ObjectUtils;
import com.wms.core.utils.jdbc.IJdbcDao;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapReactiveUserDetailsService implements ReactiveUserDetailsService {
    private final Map<String, UserDetails> users;

    /**
     * Creates a new instance using a {@link Map} that must be non blocking.
     * @param users a {@link Map} of users to use.
     */
    public MapReactiveUserDetailsService(Map<String, UserDetails> users) {
        this.users = users;
    }

    /**
     * Creates a new instance
     * @param users the {@link UserDetails} to use
     */
    public MapReactiveUserDetailsService(UserDetails... users) {
        this(Arrays.asList(users));
    }

    /**
     * Creates a new instance
     * @param users the {@link UserDetails} to use
     */
    public MapReactiveUserDetailsService(Collection<UserDetails> users) {
        Assert.notEmpty(users, "users cannot be null or empty");
        this.users = users.stream().collect(Collectors.toConcurrentMap(u -> getKey(u.getUsername()), Function.identity()));
    }

    private String getKey(String username) {
        return username.toLowerCase();
    }

    @Override
    public Mono<UserDetails> findByUsername(String username){
        IJdbcDao jdbcDao = (IJdbcDao)new ApplicationContextUtil().getApplicationContext().getBean("jdbcDao");
        Map<String,Object> userRs = null;
        List<Map<String,Object>> authorityRs = null;
        List<String> roles = new ArrayList<String>();
        UserDetails user = null;
        final String getUserListQuery = "select * from t_accounts t where t.username= '?'";
        final String getAuthoritiesByUsernameQuery = "select u.username,r.rname from t_authorities a inner join t_accounts u on a.account_id=u.id inner join t_roles r on a.role_id=r.id where u.username= '?'";
        try {
            userRs = jdbcDao.queryForMap(getUserListQuery.replace("?", username));
        }catch (WMSException ex){}

        if(ObjectUtils.isNotEmpty(userRs)){
                String userName = userRs.get("username").toString();
                try {
                    authorityRs = jdbcDao.queryForList(getAuthoritiesByUsernameQuery.replace("?", username));
                }catch (WMSException ex){
                    return Mono.empty();
                }
                if(ObjectUtils.isNotEmpty(authorityRs)) {
                    for (Map<String, Object> authority : authorityRs) {
                        roles.add(authority.get("rname").toString());
                    }
                    UserDetails ud = User.builder().username(userName).password(userRs.get("password").toString())
                            .disabled((Boolean) userRs.get("ENABLED")).accountExpired((Boolean) userRs.get("ISACCOUNTNONEXPIRED"))
                            .accountLocked((Boolean) userRs.get("ISACCOUNTNONLOCKED"))
                            .credentialsExpired((Boolean) userRs.get("ISCREDENTIALSNONEXPIRED"))
                            .roles(roles.toArray(new String[roles.size()]))
                            .passwordEncoder(password -> "{bcrypt}" + password)
                            .build();
                    user = ud;
                }
        }
        return user == null ? Mono.empty() : Mono.just(user);
    }

}
