package com.yunfeng.mall.fastdfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.yunfeng.mapper")
@ComponentScan(basePackages = {"com.yunfeng", "org.n3r.idworker"})
public class MallFastdfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallFastdfsApplication.class, args);
    }

}
