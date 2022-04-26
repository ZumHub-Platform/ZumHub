package com.application.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Lifecycle {

    private final Logger logger = LoggerFactory.getLogger(Lifecycle.class);

    public abstract void start();

    public abstract void stop();

    public Logger getLogger() {
        return logger;
    }
}
