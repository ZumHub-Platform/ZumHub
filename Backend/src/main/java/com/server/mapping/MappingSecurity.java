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

    public MappingSecurity(SecurityPolicy parent, SecurityPolicy child) {
        this.allowedOrigins = parent.allowedOrigins() == null || parent.allowedOrigins().length == 0 ?
                child.allowedOrigins() : parent.allowedOrigins();
        this.allowCredentials = parent.allowCredentials();
        this.allowedMethods = parent.allowedMethods() == null || parent.allowedMethods().length == 0 ?
                child.allowedMethods() : parent.allowedMethods();
        this.allowedHeaders = parent.allowedHeaders() == null || parent.allowedHeaders().length == 0 ?
                child.allowedHeaders() : parent.allowedHeaders();
        this.exposedHeaders = parent.exposedHeaders() == null || parent.exposedHeaders().length == 0 ?
                child.exposedHeaders() : parent.exposedHeaders();
        this.maxAge = parent.maxAge();
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

    public boolean isAllowedOrigin(String origin) {
        if (origin == null) {
            return false;
        }

        if (allowedOrigins == null || allowedOrigins.length == 0) {
            return true;
        }

        for (String allowedOrigin : allowedOrigins) {
            if (origin.equalsIgnoreCase(allowedOrigin)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    public String[] getAllowedMethods() {
        return allowedMethods;
    }

    public boolean isAllowedMethod(String method) {
        if (method == null) {
            return false;
        }

        if (allowedMethods == null || allowedMethods.length == 0) {
            return true;
        }

        for (String allowedMethod : allowedMethods) {
            if (method.equalsIgnoreCase(allowedMethod)) {
                return true;
            }
        }
        return false;
    }


    public String[] getAllowedHeaders() {
        return allowedHeaders;
    }

    public boolean isAllowedHeader(String header) {
        if (header == null) {
            return false;
        }

        if (allowedHeaders == null || allowedHeaders.length == 0) {
            return true;
        }

        for (String allowedHeader : allowedHeaders) {
            if (header.equalsIgnoreCase(allowedHeader)) {
                return true;
            }
        }
        return false;
    }


    public String[] getExposedHeaders() {
        return exposedHeaders;
    }

    public boolean isExposedHeader(String header) {
        if (header == null) {
            return false;
        }

        if (exposedHeaders == null || exposedHeaders.length == 0) {
            return true;
        }

        for (String exposedHeader : exposedHeaders) {
            if (header.equalsIgnoreCase(exposedHeader)) {
                return true;
            }
        }
        return false;
    }


    public int getMaxAge() {
        return maxAge;
    }
}
