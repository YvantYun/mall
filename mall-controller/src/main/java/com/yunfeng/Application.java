package com.yunfeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
