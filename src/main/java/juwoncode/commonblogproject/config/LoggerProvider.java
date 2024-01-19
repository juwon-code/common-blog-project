package juwoncode.commonblogproject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerProvider {
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
