package com.tomasky.departure.helper;

import com.tomasky.departure.common.config.SystemConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by sam on 2020-01-07.15:32
 */
@Component
public class SystemHelper {
    @Resource
    private SystemConfig systemConfig;

    /**
     * 根据不同的环境获取不同的域名
     * @return
     */
    public String getDomainPath() {
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(systemConfig.getEnv());
        pathBuilder.append(".fanqiele.com/");
        return pathBuilder.toString();
    }
}
