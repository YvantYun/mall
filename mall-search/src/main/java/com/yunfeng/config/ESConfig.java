package com.yunfeng.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2020-03-08
 */

@Configuration
public class ESConfig {

    /**
     * 解决netty引起弟弟issue
     */

    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}
