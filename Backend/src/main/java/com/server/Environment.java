package com.server;

import com.application.Initializer;
import com.server.request.RequestType;
import com.utilities.FileUtilities;
import io.netty.util.AsciiString;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Environment {

    private final Properties properties = new Properties();

    private final List<AsciiString> allowedHeaders = new ArrayList<>();
    private final List<RequestType> allowedRequestTypes = new ArrayList<>();
    private final List<String> crossOriginDomains = new ArrayList<>();

    private boolean accessControlAllowCredentials;
    private boolean accessControlAllowCrossOrigin;
    private int accessControlMaxAge;

    private File homeDirectory;

    public Environment() {
    }

    public Environment(InputStream propertiesStream) {
        initialize(propertiesStream);
    }

    public void initialize(InputStream propertiesStream) {
        homeDirectory = FileUtilities.getJarDirectory(Initializer.class);

        try {
            properties.load(propertiesStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getProperty(String key) {
        return (T) properties.get(key);
    }

    public <T> T getPropertyOrDefault(String key, T defaultValue) {
        return (T) properties.getOrDefault(key, defaultValue);
    }

    public List<AsciiString> getAllowedHeaders() {
        return allowedHeaders;
    }

    public Environment setAllowedHeaders(AsciiString header) {
        allowedHeaders.add(header);
        return this;
    }

    public Environment setDefaultHeaders(AsciiString... headers) {
        Collections.addAll(allowedHeaders, headers);
        return this;
    }

    public boolean isDefaultHeader(AsciiString header) {
        return allowedHeaders.contains(header);
    }

    public List<RequestType> getAllowedRequestTypes() {
        return allowedRequestTypes;
    }

    public Environment setAllowedRequestTypes(RequestType... types) {
        Collections.addAll(allowedRequestTypes, types);
        return this;
    }

    public Environment setAllowedRequestType(RequestType type) {
        allowedRequestTypes.add(type);
        return this;
    }

    public boolean isRequestTypeAllowed(RequestType type) {
        return allowedRequestTypes.contains(type);
    }

    public List<String> getCrossOriginDomains() {
        return crossOriginDomains;
    }

    public Environment setCrossOriginDomains(String domain) {
        crossOriginDomains.add(domain);
        return this;
    }

    public Environment setCrossOriginDomains(String... domains) {
        Collections.addAll(crossOriginDomains, domains);
        return this;
    }

    public boolean isCrossOriginDomainAllowed(String domain) {
        return crossOriginDomains.contains(domain);
    }

    public Properties getProperties() {
        return properties;
    }

    public File getHomeDirectory() {
        return homeDirectory;
    }

    public boolean isAccessControlAllowCredentials() {
        return accessControlAllowCredentials;
    }

    public Environment setAccessControlAllowCredentials(boolean accessControlAllowCredentials) {
        this.accessControlAllowCredentials = accessControlAllowCredentials;
        return this;
    }

    public boolean isAccessControlAllowCrossOrigin() {
        return accessControlAllowCrossOrigin;
    }

    public Environment setAccessControlAllowCrossOrigin(boolean accessControlAllowCrossOrigin) {
        this.accessControlAllowCrossOrigin = accessControlAllowCrossOrigin;
        return this;
    }

    public int getAccessControlMaxAge() {
        return accessControlMaxAge;
    }

    public Environment setAccessControlMaxAge(int accessControlMaxAge) {
        this.accessControlMaxAge = accessControlMaxAge;
        return this;
    }
}
