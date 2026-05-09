package com.instamart.auth_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // public String generateJwtToken(Authentication authentication) {
    // UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

    // List<String> roles = userPrincipal.getAuthorities().stream()
    // .map(GrantedAuthority::getAuthority)
    // .collect(Collectors.toList());

    // return Jwts.builder()
    // .setSubject(userPrincipal.getUsername())
    // .claim("roles", roles)
    // .setIssuedAt(new Date())
    // .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
    // .signWith(getSigningKey(), SignatureAlgorithm.HS256)
    // .compact();
    // }
    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}