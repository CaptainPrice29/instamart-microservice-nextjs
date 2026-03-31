package com.instamart.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchange -> exchange

                        .pathMatchers("/**").permitAll()
                        .pathMatchers("/auth/**").permitAll()
                        .pathMatchers("/products/**").permitAll()
                        .pathMatchers("/orders/**").permitAll()
                        .pathMatchers("/users/**").authenticated()
                        .anyExchange().authenticated())

                .build();
    }
}
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class SecurityConfig {

// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {

// http
// .csrf(csrf -> csrf.disable())
// .authorizeHttpRequests(auth -> auth
// // permit all to testing, later we will secure these endpoints
// .requestMatchers("/**").permitAll()
// // .requestMatchers("/auth/**").permitAll()
// .requestMatchers("/products/**").permitAll()
// // .requestMatchers("/users/**").permitAll()
// .anyRequest().authenticated());

// return http.build();
// }
// }