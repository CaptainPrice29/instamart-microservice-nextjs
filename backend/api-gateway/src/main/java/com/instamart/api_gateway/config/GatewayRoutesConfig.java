package com.instamart.api_gateway.config;

import com.instamart.api_gateway.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
@RequiredArgsConstructor
public class GatewayRoutesConfig {

        private final JwtAuthenticationFilter jwtFilter;

        @Bean
        public RouteLocator customRoutes(RouteLocatorBuilder builder) {

                return builder.routes()

                                .route("auth-service", r -> r
                                                .path("/api/auth/**")
                                                .uri("lb://AUTH-SERVICE"))

                                .route("user-service", r -> r
                                                .path("/api/users")
                                                .filters(f -> f.filter(jwtFilter.filter()))
                                                .uri("lb://USER-SERVICE"))

                                .route("product-service", r -> r
                                                .path("/api/products/**")
                                                .filters(f -> f.filter(jwtFilter.filter()))
                                                .uri("lb://PRODUCT-SERVICE"))

                                .route("order-service", r -> r
                                                .path("/api/orders/**")
                                                .filters(f -> f.filter(jwtFilter.filter()))
                                                .uri("lb://ORDER-SERVICE"))

                                .build();
        }
}