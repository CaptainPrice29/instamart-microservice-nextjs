package com.instamart.api_gateway.constants;

public class SecurityConstants {
    public static final String SECRET = "mySecretKey";

    public static final String HEADER = "Authorization";

    public static final String PREFIX = "Bearer ";

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }
}
