package com.instamart.api_gateway.filter;

import com.instamart.api_gateway.util.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public GatewayFilter filter() {

        return (exchange, chain) -> {

            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            String token = authHeader.substring(7);

            if (!jwtUtil.validateToken(token)) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            return chain.filter(exchange);
        };
    }
}