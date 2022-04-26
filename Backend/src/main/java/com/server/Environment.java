package com.server;

import io.netty.util.AsciiString;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Environment {

    private final Properties properties = new Properties();
    private final Map<AsciiString, String> defaultHeaders = new HashMap<>();

    public Environment() {
    }

    public Environment(InputStream propertiesStream) {
        initialize(propertiesStream);
    }

    public void initialize(InputStream propertiesStream) {
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

    public Environment setDefaultHeader(AsciiString name, String value) {
        defaultHeaders.put(name, value);
        return this;
    }

    public String getDefaultHeader(AsciiString name) {
        return defaultHeaders.get(name);
    }

    public Properties getProperties() {
        return properties;
    }

    public Map<AsciiString, String> getDefaultHeaders() {
        return defaultHeaders;
    }
}
