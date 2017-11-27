package cn.ylapl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author yangle
 * @version $Id SwaggerConfig.java, v 0.1 2017-02-05 下午3:17 yangle Exp $$
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket demoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("demo")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.ylapl.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(demoApiInfo());
    }

    private ApiInfo demoApiInfo() {
        Contact contact = new Contact("Angle", "http://blog.ylapl.cn", "1024920977@qq.com");
        return new ApiInfo(
                "",//大标题
                "",//小标题
                "1.0",//版本
                "逗神科技",
                contact,//作者
                "The Apache License, Version 2.0",//链接显示文字
                "http://www.apache.org/licenses/LICENSE-2.0.html",//网站链接
                Collections.emptyList()
        );
    }
}