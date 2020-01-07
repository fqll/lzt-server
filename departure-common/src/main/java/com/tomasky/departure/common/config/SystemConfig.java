package com.tomasky.departure.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by sam on 2020-01-07.15:11
 */
@Component
public class SystemConfig {
    @Value("${sys.env}")
    private String env;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}
