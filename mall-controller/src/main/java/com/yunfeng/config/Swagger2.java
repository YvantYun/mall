package com.yunfeng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.DocumentType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2019-11-15
 */

@Configuration
@EnableSwagger2
public class Swagger2 {



    // 配置swagger2的核心配置 docket
    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)   // 指定api类型为swagger2
                .apiInfo(apiInfo())                      // 用于api文档汇总信息
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yunfeng.controller")) //指定controller
                .paths(PathSelectors.any())             // 所有的controller
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("电商平台接口api")                 //文档页标题
                .contact(new Contact("云峰",
                        "https://wwww.yunfeng.com",
                        "374932573@qq.cpm"))        //联系人信息
                .description("为电商平台提供是api文档")       //详细信息
                .version("1.0.1")
                .termsOfServiceUrl("https://wwww.yunfeng.com")
                .build();
    }

}
