package com.yunfeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @author yunfeng
 * @since 2019-11-12
 */
@SpringBootApplication
// 扫描mybatis通用mapper 所在的包
@MapperScan(basePackages = "com.yunfeng.mapper")
@ComponentScan(basePackages = {"com.yunfeng", "org.n3r.idworker"})
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
