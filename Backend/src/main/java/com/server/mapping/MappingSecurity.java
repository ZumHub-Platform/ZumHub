package com.server.mapping;

import com.server.mapping.annotation.SecurityPolicy;

public class MappingSecurity {

    private final String[] allowedOrigins;
    private final boolean allowCredentials;
    private final String[] allowedMethods;
    private final String[] allowedHeaders;
    private final String[] exposedHeaders;
    private final int maxAge;

    public MappingSecurity(SecurityPolicy securityPolicy) {
        this.allowedOrigins = securityPolicy.allowedOrigins();
        this.allowCredentials = securityPolicy.allowCredentials();
        this.allowedMethods = securityPolicy.allowedMethods();
        this.allowedHeaders = securityPolicy.allowedHeaders();
        this.exposedHeaders = securityPolicy.exposedHeaders();
        this.maxAge = securityPolicy.maxAge();
    }

    public MappingSecurity(String[] allowedOrigins, boolean allowCredentials, String[] allowedMethods,
                           String[] allowedHeaders, String[] exposedHeaders, int maxAge) {
        this.allowedOrigins = allowedOrigins;
        this.allowCredentials = allowCredentials;
        this.allowedMethods = allowedMethods;
        this.allowedHeaders = allowedHeaders;
        this.exposedHeaders = exposedHeaders;
        this.maxAge = maxAge;
    }

    public String[] getAllowedOrigins() {
        return allowedOrigins;
    }

    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    public String[] getAllowedMethods() {
        return allowedMethods;
    }

    public String[] getAllowedHeaders() {
        return allowedHeaders;
    }

    public String[] getExposedHeaders() {
        return exposedHeaders;
    }

    public int getMaxAge() {
        return maxAge;
    }
}
