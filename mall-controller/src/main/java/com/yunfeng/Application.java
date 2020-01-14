package com.yunfeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
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
@MapperScan(basePackages = "com.yunfeng.mapper")
@ComponentScan(basePackages = {"com.yunfeng", "org.n3r.idworker"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
