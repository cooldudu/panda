package com.wms.core.security;

import java.security.Principal;
import java.util.Base64;
import java.util.function.Function;

import com.wms.core.utils.common.ObjectUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * Converts from a {@link ServerWebExchange} to an {@link Authentication} that can be authenticated.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class ServerMonoAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

    public static final String BASIC = "Basic ";

    @Override
    public Mono<Authentication> apply(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(authorization == null) {
            return Mono.empty();
        }

        String credentials = authorization.length() <= BASIC.length() ?
                "" : authorization.substring(BASIC.length(), authorization.length());
        byte[] decodedCredentials = base64Decode(credentials);
        String decodedAuthz = new String(decodedCredentials);
        String[] userParts = decodedAuthz.split(":");

        if(userParts.length != 2) {
            return Mono.empty();
        }

        String username = userParts[0];
        String password = userParts[1];

        return Mono.just(new UsernamePasswordAuthenticationToken(username, password));
    }

    public static String extract(ServerWebExchange serverWebExchange) {
        String authorization = serverWebExchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
        return ObjectUtils.isNotEmpty(authorization)?authorization:"";
    }

    private byte[] base64Decode(String value) {
        try {
            return Base64.getDecoder().decode(value);
        } catch(Exception e) {
            return new byte[0];
        }
    }
}