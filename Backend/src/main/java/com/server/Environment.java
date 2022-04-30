package com.server;

import com.application.Initializer;
import com.utilities.FileUtilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Environment {

    private final Properties properties = new Properties();

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

    public Properties getProperties() {
        return properties;
    }

    public File getHomeDirectory() {
        return homeDirectory;
    }
}
